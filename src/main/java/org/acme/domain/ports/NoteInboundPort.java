package org.acme.domain.ports;

import jakarta.ws.rs.PathParam;
import org.acme.domain.model.Note;
import org.acme.inbound.dto.NoteDTOResponse;

import java.util.List;

public interface NoteInboundPort {
    List<NoteDTOResponse> getAllNotes();

    NoteDTOResponse updateNoteText(long noteId, Note note);
    NoteDTOResponse deleteNote(long noteId);
}
