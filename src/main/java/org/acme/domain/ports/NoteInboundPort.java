package org.acme.domain.ports;

import org.acme.domain.model.Note;

import java.util.List;

public interface NoteInboundPort {
    List<Note> getAllNotes();
}
