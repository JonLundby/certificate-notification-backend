package org.acme.inbound;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.ports.CertificateInboundPort;

import java.util.List;

@ApplicationScoped
@Path("certificates")
public class CertificateInboundAdapter {

    @Inject
    CertificateInboundPort certificateInboundPort;

    @GET()
    public List<CertificateMetadata> getAllCertificates() {
        return certificateInboundPort.getAllCertificates();
    }

}
