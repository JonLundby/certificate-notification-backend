package org.acme.inbound;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.domain.ports.CertificateInboundPort;

@ApplicationScoped
@Path("certificates")
public class CertificateInboundAdapter {

    @Inject
    CertificateInboundPort certificateInboundPort;

    @GET
    public String getAllCertificates() {
        return certificateInboundPort.getAllCertificates();
    }

}
