package org.acme.inbound.mapper;

import org.acme.domain.dto.NoteDTO;
import org.acme.domain.model.Note;
import org.acme.outbound.model.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface NoteMapper {
    NoteEntity toEntity(Note note);
    @Mapping(target = "certificateMetadataEntity", ignore = true)
    Note toDomain(NoteEntity noteEntity);
    List<Note> toDomainList(List<NoteEntity> noteEntities);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "certificateMetadataEntity", ignore = true)
    Note toDomainFromDTO(NoteDTO noteDTO);
}
