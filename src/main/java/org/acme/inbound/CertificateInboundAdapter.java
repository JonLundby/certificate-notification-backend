package org.acme.inbound;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.dto.NoteDTO;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.Note;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.inbound.mapper.NoteMapper;

import java.util.List;

@ApplicationScoped
@Path("certificates")
public class CertificateInboundAdapter {

    @Inject
    NoteMapper noteMapper;

    @Inject
    CertificateInboundPort certificateInboundPort;

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public List<CertificateMetadata> getAllCertificates() {
        return certificateInboundPort.getAllCertificates();
    }

    @POST
    @Path("/{id}/notes")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNoteToCertificate(@PathParam("id") long certificateId, NoteDTO noteDto) {
        Note note = noteMapper.toDomainFromDTO(noteDto);
        certificateInboundPort.addNoteToCertificate(certificateId, note);
    }
}
