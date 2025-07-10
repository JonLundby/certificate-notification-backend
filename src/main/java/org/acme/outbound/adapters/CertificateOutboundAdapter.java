package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.inbound.dto.CertificateDTOResponse;
import org.acme.inbound.dto.CertificateUpdateDTO;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.Note;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.inbound.dto.NoteDTOResponse;
import org.acme.inbound.mapper.CertificateMetadataMapper;
import org.acme.inbound.mapper.NoteMapper;
import org.acme.outbound.model.CertificateMetadataEntity;
import org.acme.outbound.model.NoteEntity;
import org.acme.outbound.repository.CertificateMetadataRepository;

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

    public List<CertificateDTOResponse> findAllWithDetails() {
        List<CertificateMetadataEntity> entities = certificateMetadataRepository.findAllWithDetails();
        return certificateMetadataMapper.toCertificateDTOResponseList(entities);
    }

    @Override
    public CertificateMetadata persist(CertificateMetadata certificateMetadata) {
        // Map metadata to entity
        CertificateMetadataEntity entity = certificateMetadataMapper.toEntity(certificateMetadata);

        // save the entity
        certificateMetadataRepository.persist(entity);

        // Flush transaction to make sure certificate insert hits DB and entity is persisted...
        // ...if not flushed then the scanned URI won't know what certificate id to relate to since id has not...
        // ...yet been created due to being in transactional state
        certificateMetadataRepository.flush();

        // return the entity mapped back into a domain model
        return certificateMetadataMapper.toDomain(entity); // map back with ID
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
    public NoteDTOResponse addNoteToCertificate(long certificateId, Note note) {

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
        // hibernate needs to flush the transaction so that the noteEntity will get its generated ID populated...
        // ...if no flush then hibernate will postpone the operation (and setting generated ID!) for efficiency
        certificateMetadataRepository.flush();

        return noteMapper.toNoteDTOResponse(noteEntity);
    }

    @Override
    @Transactional
    public void updateEditableCertificateProperties(long certificateId, CertificateUpdateDTO certificateUpdateDTO) {
        CertificateMetadataEntity entity = certificateMetadataRepository.findById(certificateId);
        if (entity == null) {
            throw new IllegalArgumentException("Could not find certificate with id: " + certificateId);
        }
        // TODO: Consider mapping from dto(or rather domain model due to possible corresponding future changes in the inbound adapter) to domain model if need...
        //  ...for more editable properties and/or service logic occurs

        entity.setCertificateLocation(certificateUpdateDTO.getCertificateLocation());
        entity.setPasswordLocation(certificateUpdateDTO.getPasswordLocation());
        entity.setPrivateKeyLocation(certificateUpdateDTO.getPrivateKeyLocation());

        certificateMetadataRepository.persist(entity);
    }

}
