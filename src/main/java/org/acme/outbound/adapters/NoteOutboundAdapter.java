package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.model.Note;
import org.acme.domain.ports.NoteOutboundPort;
import org.acme.inbound.dto.NoteDTORequest;
import org.acme.inbound.dto.NoteDTOResponse;
import org.acme.inbound.mapper.NoteMapper;
import org.acme.outbound.model.NoteEntity;
import org.acme.outbound.repository.NoteRepository;

import java.util.List;

@ApplicationScoped
public class NoteOutboundAdapter implements NoteOutboundPort {

    @Inject
    NoteRepository noteRepository;

    @Inject
    NoteMapper noteMapper;

    @Override
    public List<NoteDTOResponse> getAllNotes() {
        return noteMapper.toNoteDTOResponseList(noteRepository.findAll().list());
    }

    @Override
    @Transactional
    public NoteDTOResponse updateNoteText(long noteId, Note note) {
        NoteEntity noteEntity = noteRepository.findById(noteId);

        if (noteEntity == null) {
            throw new RuntimeException("Could not find note wih note id: " + noteId);
        }

        noteEntity.setLocalDateTimeStamp(note.getLocalDateTimeStamp());
        noteEntity.setText(note.getText());
        noteRepository.persist(noteEntity);
        noteRepository.flush();

        return noteMapper.toNoteDTOResponse(noteEntity);
    }

    @Override
    @Transactional
    public NoteDTOResponse deleteNote(long noteId) {
        NoteEntity noteEntity = noteRepository.findById(noteId);

        if (noteEntity == null) {
            throw new RuntimeException("Could not find note wih note id: " + noteId);
        }

        noteRepository.delete(noteEntity);

        return noteMapper.toNoteDTOResponse(noteEntity);
    }
}
