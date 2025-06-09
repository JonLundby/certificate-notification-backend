package org.acme.outbound.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.CertificateMetadata;

import java.util.Optional;

@ApplicationScoped
public class CertificateMetadataRepository implements PanacheRepository<CertificateMetadata> {
    public Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId) {
        return Optional.ofNullable(find("issuerSerialNumberId", issuerSerialNumberId).firstResult());
    }
}
