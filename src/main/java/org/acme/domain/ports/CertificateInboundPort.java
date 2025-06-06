package org.acme.domain.ports;

public interface CertificateInboundPort {
    String getAllCertificates();
    void retrieveCertificateMetadataDelegator(String uri);
}
