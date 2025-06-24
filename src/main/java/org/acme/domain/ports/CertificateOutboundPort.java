package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CertificateOutboundPort {
    CertificateMetadata persist(CertificateMetadata certificateMetadata);
    Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId);
    void updateNotifiedAt(CertificateMetadata certificateMetadata, LocalDateTime now, int daysNotification);
}
