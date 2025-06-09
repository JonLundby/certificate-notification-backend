package org.acme.domain.ports;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.UriEntity;

import java.util.List;

public interface UriOutboundPort{
    // All panacheRepository default methods must be declared here to open the usage of them in uriOutboundPort
    void persist(List<UriEntity> uriEntity);
    UriEntity findByUri(String uri);
}
