package org.acme.domain.ports;

import org.acme.domain.model.UriEntity;

import java.util.List;

public interface UriInboundPort {
    List<UriEntity> createURIs(String uriStr);
}