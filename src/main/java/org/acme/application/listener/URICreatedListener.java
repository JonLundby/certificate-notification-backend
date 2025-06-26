package org.acme.application.listener;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.event.UriCreated;
import org.acme.domain.ports.CertificateInboundPort;

@ApplicationScoped
public class URICreatedListener {

    @Inject
    CertificateInboundPort certificateInboundPort;

    @Transactional // Transactional since it is sort of like an inbound adapter within the domain/service layer
    void onCreatingUri(@ObservesAsync UriCreated event) {
        certificateInboundPort.retrieveCertificateMetadataDelegator(event.uri(), false);
    }
}
