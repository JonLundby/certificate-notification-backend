package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.inbound.dto.CertificateDTOResponse;
import org.acme.inbound.dto.CertificateUpdateDTO;
import org.acme.domain.model.Note;
import org.acme.inbound.dto.NoteDTOResponse;

import java.io.InputStream;
import java.util.List;

public interface CertificateInboundPort {
    List<CertificateDTOResponse> getAllCertificatesWithDetails();
    void retrieveCertificateMetadataDelegator(String uri, boolean sendNotifications);
    NoteDTOResponse addNoteToCertificate(long certificateId, Note note);
    CertificateDTOResponse updateEditableCertificateProperties(long certificateId, CertificateUpdateDTO certificateUpdateDTO);
    CertificateMetadata uploadClientCertificate(InputStream fileInputStream);
}
