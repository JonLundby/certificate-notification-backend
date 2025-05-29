package org.acme.domain.ports;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.UriEntity;

public interface UriOutboundPort extends PanacheRepository<UriEntity> {
}
