package org.acme.domain.ports;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.CertificateMetadata;

public interface CertificateOutboundPort extends PanacheRepository<CertificateMetadata> {
}
