package org.acme.domain.ports;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.CertificateMetadata;

import java.util.Optional;

public interface CertificateOutboundPort {
    void persist(CertificateMetadata certificateMetadata);
    Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId);
}
