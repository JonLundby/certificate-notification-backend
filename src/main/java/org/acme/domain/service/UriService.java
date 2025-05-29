package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.domain.model.UriEntity;
import org.acme.domain.model.enums.AllowedSchemes;
import org.acme.domain.ports.URIInboundPort;
import org.acme.domain.ports.UriOutboundPort;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class UriService implements URIInboundPort {

    @Inject
    UriOutboundPort uriOutboundPort;

    @Override
    public void createURIs(String uriStr) {

        List<UriEntity> entities = Stream.of(uriStr.split("\\R+")) // \R = split at any line-break
                .map(String::trim)                  // trim surrounding spaces
                .filter(s -> !s.isEmpty())          // filter out empty lines
                .map(UriService::asValidUri)        // throws if malformed
                .map(this::toUriEntity)                // for each 'this' in the list make this an entity
                .collect(Collectors.toList());      // collect all into a list

        uriOutboundPort.persist(entities);
    }

    /* ---------- helpers ---------- */
    private static String asValidUri(String rawUriStr) {
        URI uri;
        try {
            uri = URI.create(rawUriStr);   // syntax check
        } catch (IllegalArgumentException e) {
            // TODO: Consider making a BadRequestExceptionMapper so that the backend can send a json message back to the frontend
            throw new BadRequestException("Malformed URI: " + rawUriStr, e);
        }

        if (!AllowedSchemes.isAllowed(uri.getScheme())) {
            // TODO: Consider making a BadRequestExceptionMapper so that the backend can send a json message back to the frontend
            throw new BadRequestException(
                    "Scheme must be one of: " +
                            Stream.of(AllowedSchemes.values())
                                    .map(AllowedSchemes::getScheme)
                                    .collect(Collectors.joining(", "))
                            + "; got: " + uri.getScheme()
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
