package org.acme.inbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.model.Note;
import org.acme.domain.ports.NoteInboundPort;
import org.acme.inbound.dto.NoteDTORequest;
import org.acme.inbound.dto.NoteDTOResponse;
import org.acme.inbound.mapper.NoteMapper;

import java.util.List;

@ApplicationScoped
@Path("/notes")
public class NoteInboundAdapter {

    @Inject
    NoteInboundPort noteInboundPort;

    @Inject
    NoteMapper noteMapper;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoteDTOResponse> getAllNotes() {
        return noteInboundPort.getAllNotes();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public NoteDTOResponse updateNoteText(@PathParam("id") long noteId, NoteDTORequest noteDTORequest) {
        Note note = noteMapper.toDomainFromDTO(noteDTORequest);
        return noteInboundPort.updateNoteText(noteId, note);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NoteDTOResponse deleteNote(@PathParam("id") long noteId) {
        return noteInboundPort.deleteNote(noteId);
    }

}
