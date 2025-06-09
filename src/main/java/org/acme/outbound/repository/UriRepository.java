package org.acme.outbound.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.UriEntity;

@ApplicationScoped
public class UriRepository implements PanacheRepository<UriEntity> {
    public UriEntity findByUri(String uri) {
        return find("uri", uri).firstResult();
    }
}
