package org.acme.domain.ports;

import org.acme.inbound.dto.NoteDTOResponse;

import java.util.List;

public interface NoteOutboundPort {
    List<NoteDTOResponse> getAllNotes();
}
