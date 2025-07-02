package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.Note;
import org.acme.domain.ports.NoteOutboundPort;
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
    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll().list();
    }
}
