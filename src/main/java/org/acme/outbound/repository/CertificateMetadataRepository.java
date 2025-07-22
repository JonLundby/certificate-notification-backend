package org.acme.outbound.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.outbound.model.CertificateMetadataEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CertificateMetadataRepository implements PanacheRepository<CertificateMetadataEntity> {

    public Optional<CertificateMetadataEntity> findByIssuerSerialNumberId(String issuerSerialNumberId) {
        return Optional.ofNullable(find("issuerSerialNumberId", issuerSerialNumberId).firstResult());
    }

    // FIND ALL CERTIFICATES WITH RELATED URIs & NOTES
    public List<CertificateMetadataEntity> findAllWithDetails() {
        return list("SELECT DISTINCT c FROM CertificateMetadataEntity c " +
                "LEFT JOIN FETCH c.uris " +
                "LEFT JOIN FETCH c.notes");
    }
}
