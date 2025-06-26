package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.inbound.mapper.CertificateMetadataMapper;
import org.acme.outbound.model.CertificateMetadataEntity;
import org.acme.outbound.repository.CertificateMetadataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CertificateOutboundAdapter implements CertificateOutboundPort {

    @Inject
    CertificateMetadataMapper certificateMetadataMapper;

    @Inject
    CertificateMetadataRepository certificateMetadataRepository;

    @Override
    public List<CertificateMetadata> findAll() {
        List<CertificateMetadata> certificateMetadataList = certificateMetadataMapper.toCertificateMetadataList(certificateMetadataRepository.findAll().list());
        return certificateMetadataList;
    }

    @Override
    public CertificateMetadata persist(CertificateMetadata certificateMetadata) {
        // Map metadata to entity
        CertificateMetadataEntity e = certificateMetadataMapper.toEntity(certificateMetadata);

        // save the entity
        certificateMetadataRepository.persist(e);

        // Flush transaction to make sure insert hits DB and entity is persisted
        certificateMetadataRepository.flush();

        // return the entity mapped back into a domain model
        return certificateMetadataMapper.toDomain(e); // map back with ID
    }

    @Override
    public Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId) {
        return certificateMetadataRepository.findByIssuerSerialNumberId(issuerSerialNumberId).map(certificateMetadataMapper::toDomain);
    }

    @Override
    @Transactional
    public void updateNotifiedAt(CertificateMetadata certificateMetadata, LocalDateTime now, int daysNotification) {
        Long id = certificateMetadata.getId();
        CertificateMetadataEntity entity = certificateMetadataRepository.findById(id);

        if (entity == null) {
            throw new IllegalArgumentException("Could not find certificate with id " + id);
        }

        switch (daysNotification) {
            case 60 -> entity.setNotifiedAt60Days(now);
            case 30 -> entity.setNotifiedAt30Days(now);
            case 14 -> entity.setNotifiedAt14Days(now);
            default -> throw new IllegalArgumentException("Unsupported daysNotification: " + daysNotification);
        }
    }
}
