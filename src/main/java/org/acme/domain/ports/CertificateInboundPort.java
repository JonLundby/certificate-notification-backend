package org.acme.domain.ports;

import org.acme.domain.dto.CertificateUpdateDTO;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.Note;

import java.util.List;

public interface CertificateInboundPort {
    List<CertificateMetadata> getAllCertificates();
    void retrieveCertificateMetadataDelegator(String uri, boolean sendNotifications);
    void addNoteToCertificate(long certificateId, Note note);
    void updateEditableCertificateProperties(long certificateId, CertificateUpdateDTO certificateUpdateDTO);
}
