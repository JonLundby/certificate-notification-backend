package org.acme.domain.ports;

import org.acme.domain.model.UriDomainModel;

import java.util.List;

public interface UriInboundPort {
    List<UriDomainModel> findAll();
    List<UriDomainModel> createURIs(String uriStr);
    List<UriDomainModel> dispatchAllUris(boolean sendNotifications);
}