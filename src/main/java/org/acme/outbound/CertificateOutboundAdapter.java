package org.acme.outbound;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.ports.CertificateOutboundPort;

@ApplicationScoped
public class CertificateOutboundAdapter implements CertificateOutboundPort {
}
