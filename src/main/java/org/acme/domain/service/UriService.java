package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.domain.model.UriDomainModel;
import org.acme.domain.model.enums.AllowedSchemes;
import org.acme.domain.ports.UriInboundPort;
import org.acme.domain.ports.UriOutboundPort;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class UriService implements UriInboundPort {

    private static final Logger logger = Logger.getLogger(UriService.class);

    @Inject
    UriOutboundPort uriOutboundPort;

    @Inject
    CertificateService certificateService;

    public List<UriDomainModel> findAll() {
        return uriOutboundPort.findAllUris();
    }

    @Override
    public List<UriDomainModel> dispatchAllUris(List<UriDomainModel> allUris, boolean sendNotifications) {
//        List<UriDomainModel> allUris = uriOutboundPort.findAllUris();

        allUris.forEach(uriEntity -> {
            // try catch to make sure that the foreach continues in case of fx not being able to retrieve certificate from insecure TLS 1.1
            try {
                certificateService.retrieveCertificateMetadataDelegator(uriEntity.getUri(), sendNotifications);
            } catch (Exception e) {
                logger.warn("Failed to retrieve certificate for: " + uriEntity.getUri(), e);
            }
        });

        return allUris;
    }

    @Override
    public List<UriDomainModel> createURIs(String uriStr) {
        // Split, trim, filter and validate URIs
        List<String> rawUris = Stream.of(uriStr.split("\\R+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(UriService::asValidUri)
                .toList();

        // Map to domain models
        List<UriDomainModel> uriDomainModelsToPersist = rawUris.stream()
                .map(this::toUriEntity)
                .toList();

        // Adapter handles filtering of existing URIs
        List<UriDomainModel> uriDomainModels = uriOutboundPort.persistList(uriDomainModelsToPersist);

        // Dispatch the list of newly updated URI to retrieve their certificates and set relations
        dispatchAllUris(uriDomainModels,false);


        return uriDomainModels;
    }

    /* ---------- HELPERS ---------- */
    private static String asValidUri(String rawUriStr) {
        URI uri;
        try {
            uri = URI.create(rawUriStr);   // valid uri syntax check - ensures no illegal characters
        } catch (IllegalArgumentException e) {
            logger.errorf("Malformed URI: " + e);
            throw new BadRequestException("Malformed URI: " + rawUriStr, e);
        }

        // Checks if the uri scheme is equal to one of the AllowedSchemes enum values (using hashmap in the AllowedSchemes class)
        if (!AllowedSchemes.isAllowed(uri.getScheme())) {
            String allowedList = Stream.of(AllowedSchemes.values())
                    .map(AllowedSchemes::getScheme)
                    .collect(Collectors.joining(", "));

            logger.warnf(
                    "WARNING: User provided disallowed scheme. Expected one of [%s]",
                    allowedList
            );

            throw new BadRequestException(
                    "Scheme must be one of: " + allowedList + "; got: " + uri.getScheme()
            );
        }

        // Ensure host is present and not empty
        if (uri.getHost() == null || uri.getHost().isBlank()) {
            throw new BadRequestException("Malformed URI: missing or invalid host in " + rawUriStr);
        }

        return rawUriStr;
    }

    private UriDomainModel toUriEntity(String uri) {
        UriDomainModel e = new UriDomainModel();
        e.setUri(uri);
        return e;
    }
}
