package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;

import java.util.List;

public interface CertificateInboundPort {
    List<CertificateMetadata> getAllCertificates();
    void retrieveCertificateMetadataDelegator(String uri, boolean sendNotifications);
}
