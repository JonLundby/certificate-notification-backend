package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.domain.ports.CertificateOutboundPort;

import java.net.URI;

@ApplicationScoped
public class CertificateService implements CertificateInboundPort {

    @Inject
    CertificateOutboundPort certificateOutboundPort;

    @Override
    public String getAllCertificates() {
        return "Http GET request called 'getAllCertificates'";
    }

    @Override
    public void retrieveCertificateMetadataDelegator(String uriStr) {
        // TODO: consider making unit test of retrieveCertificateMetadataDelegator and/or the retrieveHTTPSCertificateMetadata etc.

        URI uri;
        try {
            uri = URI.create(uriStr);   // valid uri syntax check
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Malformed URI: " + uriStr, e);
        }

        String scheme = uri.getScheme();
        System.out.println("Send raw uri string to " + scheme + " metadata retrieval method ---> Certificate " + uriStr + " retrieved");
    }

    private void retrieveHTTPSCertificateMetadata(String uriStr) {
        // TODO: code this method to collect certificate metadata based on the uriStr or maybe consider if it is better to use a real URI instance instead of uriStr
    }

}
