package org.acme.application.listener;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.acme.domain.event.UriCreated;
import org.acme.domain.ports.CertificateInboundPort;

@ApplicationScoped
public class URICreatedListener {

    @Inject
    CertificateInboundPort certificateInboundPort;

    void onCreatingUri(@ObservesAsync UriCreated event) {
        certificateInboundPort.retrieveCertificateMetadataDelegator(event.uri());
    }
}


