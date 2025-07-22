package org.acme.domain.ports;

import org.acme.inbound.dto.CertificateDTOResponse;
import org.acme.inbound.dto.CertificateUpdateDTO;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.Note;
import org.acme.inbound.dto.NoteDTOResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CertificateOutboundPort {
    List<CertificateDTOResponse> findAllWithDetails();
    List<CertificateMetadata> findValidClientCertificates();
    CertificateMetadata persist(CertificateMetadata certificateMetadata);
    Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId);
    void updateNotifiedAt(CertificateMetadata certificateMetadata, LocalDateTime now, int daysNotification);
    NoteDTOResponse addNoteToCertificate(long certificateId, Note note);
    CertificateDTOResponse updateEditableCertificateProperties(long certificateId, CertificateUpdateDTO certificateUpdateDTO);
}
