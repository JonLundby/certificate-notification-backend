package org.acme.domain.ports;

import org.acme.domain.dto.CertificateUpdateDTO;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.Note;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CertificateOutboundPort {
    List<CertificateMetadata> findAll();
    CertificateMetadata persist(CertificateMetadata certificateMetadata);
    Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId);
    void updateNotifiedAt(CertificateMetadata certificateMetadata, LocalDateTime now, int daysNotification);
    void addNoteToCertificate(long certificateId, Note note);
    void updateEditableCertificateProperties(long certificateId, CertificateUpdateDTO certificateUpdateDTO);
}
