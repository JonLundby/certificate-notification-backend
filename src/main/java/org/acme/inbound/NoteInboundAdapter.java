package org.acme.inbound;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.model.Note;
import org.acme.domain.ports.NoteInboundPort;

import java.util.List;

@ApplicationScoped
@Path("/notes")
public class NoteInboundAdapter {

    @Inject
    NoteInboundPort noteInboundPort;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getAllNotes() {
        return noteInboundPort.getAllNotes();
    }
}
