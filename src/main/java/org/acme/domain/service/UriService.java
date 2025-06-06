package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.enterprise.event.Event;
import org.acme.domain.model.UriEntity;
import org.acme.domain.model.enums.AllowedSchemes;
import org.acme.domain.event.UriCreated;
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
    Event<UriCreated> uriCreatedEvent;

    @Override
    @Transactional
    public List<UriEntity> createURIs(String uriStr) {

        List<UriEntity> entities = Stream.of(uriStr.split("\\R+")) // \R = split at any line-break
                .map(String::trim)                                      // trim surrounding spaces
                .filter(s -> !s.isEmpty())                              // filter out empty lines
                .map(UriService::asValidUri)                            // throws if malformed
                .map(this::toUriEntity)                                 // for each 'this' in the list make this a UriEntity
                .collect(Collectors.toList());                          // collect all into a list

        uriOutboundPort.persist(entities);

        // Fire & forget to decouple the certificate retrieval from this methods
        entities.forEach(e -> uriCreatedEvent.fireAsync(new UriCreated(e.getUri())));

        return entities;
    }

    /* ---------- HELPERS ---------- */
    private static String asValidUri(String rawUriStr) {
        URI uri;
        try {
            uri = URI.create(rawUriStr);   // valid uri syntax check
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

            //TODO: return bad request WITH PAYLOAD to client
            throw new BadRequestException(
                    "Scheme must be one of: " + allowedList + "; got: " + uri.getScheme()
            );
        }

        return rawUriStr;
    }

    private UriEntity toUriEntity(String uri) {
        UriEntity e = new UriEntity();
        e.setUri(uri);
        return e;
    }
}
