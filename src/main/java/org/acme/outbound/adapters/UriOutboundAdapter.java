package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.model.UriDomainModel;
import org.acme.domain.ports.UriOutboundPort;
import org.acme.inbound.mapper.UriMapper;
import org.acme.outbound.model.CertificateMetadataEntity;
import org.acme.outbound.model.UriEntity;
import org.acme.outbound.repository.CertificateMetadataRepository;
import org.acme.outbound.repository.UriRepository;

import java.util.List;

@ApplicationScoped
public class UriOutboundAdapter implements UriOutboundPort {

    @Inject
    UriMapper uriMapper;

    @Inject
    UriRepository uriRepository;

    @Inject
    CertificateMetadataRepository certificateMetadataRepository;

    @Override
    @Transactional
    public List<UriDomainModel> persistList(List<UriDomainModel> uriDomainModels) {
        List<UriEntity> newEntities = uriDomainModels.stream()
                .map(uriMapper::toUriEntity)
                .filter(entity -> uriRepository.find("uri", entity.getUri()).firstResult() == null) // skip existing
                .toList();

        if (!newEntities.isEmpty()) {
            uriRepository.persist(newEntities);
        }

        return newEntities.stream().map(uriMapper::toDomain).toList();
    }

    @Override
    public UriDomainModel findByUri(String uri) {
        UriEntity e = uriRepository.find("uri", uri).firstResult();
        return uriMapper.toDomain(e);
    }

    @Override
    public List<UriDomainModel> findAllUris() {
        List<UriEntity> uriEntities = uriRepository.findAll().list();
        return uriMapper.toUriDomainList(uriEntities);
    }

    @Override
    @Transactional
    public void updateCertificateRelation(String uriStr, Long certMetadataEntityId) {
        UriEntity uriEntity = uriRepository.find("uri", uriStr).firstResult();
        if (uriEntity == null) {
            throw new IllegalArgumentException("URI not found: " + uriStr);
        }

        // Properly fetch the managed CertificateMetadataEntity
        CertificateMetadataEntity certEntity = certificateMetadataRepository.findById(certMetadataEntityId);
        if (certEntity == null) {
            throw new IllegalArgumentException("Certificate metadata not found: ID = " + certMetadataEntityId);
        }

        // Set the managed object (no TransientObjectException)
        uriEntity.setCertificateMetadataEntity(certEntity);
    }
}
