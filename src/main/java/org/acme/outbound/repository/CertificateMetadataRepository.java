package org.acme.outbound.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.outbound.model.CertificateMetadataEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CertificateMetadataRepository implements PanacheRepository<CertificateMetadataEntity> {

    @PersistenceContext
    EntityManager entityManager;

    public Optional<CertificateMetadataEntity> findByIssuerSerialNumberId(String issuerSerialNumberId) {
        return Optional.ofNullable(find("issuerSerialNumberId", issuerSerialNumberId).firstResult());
    }

    // FIND ALL CERTIFICATES WITH RELATED URIs & NOTES
    public List<CertificateMetadataEntity> findAllWithDetails() {
        return entityManager.createQuery(
                        "SELECT DISTINCT c FROM CertificateMetadataEntity c " +
                                "LEFT JOIN FETCH c.uris " +
                                "LEFT JOIN FETCH c.notes", CertificateMetadataEntity.class)
                .getResultList();
    }
}
