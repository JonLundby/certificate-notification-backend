package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.Note;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.inbound.mapper.CertificateMetadataMapper;
import org.acme.inbound.mapper.NoteMapper;
import org.acme.outbound.model.CertificateMetadataEntity;
import org.acme.outbound.model.NoteEntity;
import org.acme.outbound.repository.CertificateMetadataRepository;
import org.acme.outbound.repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CertificateOutboundAdapter implements CertificateOutboundPort {

    @Inject
    CertificateMetadataMapper certificateMetadataMapper;

    @Inject
    NoteMapper noteMapper;

    @Inject
    CertificateMetadataRepository certificateMetadataRepository;

    @Inject
    NoteRepository noteRepository;

    @Override
    public List<CertificateMetadata> findAll() {
        // TODO: this returns all certificateMetadataEntities but with empty 'uris' property due to mapstruct ignore uris for cyclic incidents.
        //  Consider making custom mapping if uris should be populated properly (uriEntities carry FK to certificateMetadata so that will do for now)
        return certificateMetadataMapper.toCertificateMetadataList(certificateMetadataRepository.findAll().list());
    }

    @Override
    public CertificateMetadata persist(CertificateMetadata certificateMetadata) {
        // Map metadata to entity
        CertificateMetadataEntity e = certificateMetadataMapper.toEntity(certificateMetadata);

        // save the entity
        certificateMetadataRepository.persist(e);

        // Flush transaction to make sure insert hits DB and entity is persisted
        certificateMetadataRepository.flush();

        // return the entity mapped back into a domain model
        return certificateMetadataMapper.toDomain(e); // map back with ID
    }

    @Override
    public Optional<CertificateMetadata> findByIssuerSerialNumberId(String issuerSerialNumberId) {
        return certificateMetadataRepository.findByIssuerSerialNumberId(issuerSerialNumberId).map(certificateMetadataMapper::toDomain);
    }

    @Override
    @Transactional
    public void updateNotifiedAt(CertificateMetadata certificateMetadata, LocalDateTime now, int daysNotification) {
        Long id = certificateMetadata.getId();
        CertificateMetadataEntity entity = certificateMetadataRepository.findById(id);

        if (entity == null) {
            throw new IllegalArgumentException("Could not find certificate with id " + id);
        }

        switch (daysNotification) {
            case 60 -> entity.setNotifiedAt60Days(now);
            case 30 -> entity.setNotifiedAt30Days(now);
            case 14 -> entity.setNotifiedAt14Days(now);
            default -> throw new IllegalArgumentException("Unsupported daysNotification: " + daysNotification);
        }
    }

    @Override
    @Transactional
    public void addNoteToCertificate(long certificateId, Note note) {

        // Load the owning certificate from the database
        CertificateMetadataEntity certificateMetadataEntity = certificateMetadataRepository.findById(certificateId);
        if (certificateMetadataEntity == null) {
            throw new IllegalArgumentException("Could not find certificate with id: " + certificateId);
        }

        NoteEntity noteEntity = noteMapper.toEntity(note);

        // Setting the certificate_id FK on the note
        noteEntity.setCertificateMetadataEntity(certificateMetadataEntity);

        // triggers hibernate to update the database based on the owning certificate and persist the note through that due to cascadeType.All
        certificateMetadataEntity.getNotes().add(noteEntity);
    }
}
