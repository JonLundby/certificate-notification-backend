package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.Note;
import org.acme.domain.ports.NoteInboundPort;
import org.acme.domain.ports.NoteOutboundPort;
import org.acme.outbound.model.NoteEntity;

import java.util.List;

@ApplicationScoped
public class NoteService implements NoteInboundPort {

    @Inject
    NoteOutboundPort noteOutboundPort;

    @Override
    public List<NoteEntity> getAllNotes() {
        return noteOutboundPort.getAllNotes();
    }
}
