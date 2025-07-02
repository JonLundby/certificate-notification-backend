package org.acme.domain.ports;

import org.acme.domain.model.Note;
import org.acme.outbound.model.NoteEntity;

import java.util.List;

public interface NoteOutboundPort {
    List<NoteEntity> getAllNotes();
}
