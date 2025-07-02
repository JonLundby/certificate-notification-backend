package org.acme.inbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.model.Note;
import org.acme.domain.ports.NoteInboundPort;
import org.acme.inbound.dto.NoteDTOResponse;
import org.acme.inbound.mapper.NoteMapper;
import org.acme.outbound.model.NoteEntity;

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
        List<NoteEntity> entities = noteInboundPort.getAllNotes();
        return noteMapper.toNoteDTOResponseList(entities);
    }
}
