package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.Note;
import org.acme.domain.ports.NoteInboundPort;
import org.acme.domain.ports.NoteOutboundPort;

import java.util.List;

@ApplicationScoped
public class NoteService implements NoteInboundPort {

    @Inject
    NoteOutboundPort noteOutboundPort;

    @Override
    public List<Note> getAllNotes() {
        return noteOutboundPort.getAllNotes();
    }
}
