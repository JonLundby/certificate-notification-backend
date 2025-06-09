package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.UriOutboundPort;
import org.acme.outbound.repository.UriRepository;

import java.util.List;

@ApplicationScoped
public class UriOutboundAdapter implements UriOutboundPort {

    @Inject
    UriRepository uriRepository;

    @Override
    public void persist(List<UriEntity> uriEntity) {
        uriRepository.persist(uriEntity);
    }

    @Override
    public UriEntity findByUri(String uri) {
        return uriRepository.find("uri", uri).firstResult();
    }

    @Override
    public List<UriEntity> findAllUris() {
        return uriRepository.findAll().list();
    }
}
