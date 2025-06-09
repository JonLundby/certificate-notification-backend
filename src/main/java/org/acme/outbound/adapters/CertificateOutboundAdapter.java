package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.outbound.repository.CertificateMetadataRepository;

import java.util.Optional;

@ApplicationScoped
public class CertificateOutboundAdapter implements CertificateOutboundPort {

    @Inject
    CertificateMetadataRepository certificateMetadataRepository;

    @Override
    public void persist(CertificateMetadata certificateMetadata) {
        certificateMetadataRepository.persist(certificateMetadata);
    }

    @Override
    public Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId) {
        return certificateMetadataRepository.findByIssuerSerialNumberId(issuerSerialNumberId);
    }
}
