package org.acme.inbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.inbound.dto.CertificateDTOResponse;
import org.acme.inbound.dto.CertificateUpdateDTO;
import org.acme.inbound.dto.NoteDTORequest;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.Note;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.inbound.dto.NoteDTOResponse;
import org.acme.inbound.mapper.NoteMapper;

import java.util.List;

@ApplicationScoped
@Path("certificates")
public class CertificateInboundAdapter {

    @Inject
    NoteMapper noteMapper;

    @Inject
    CertificateInboundPort certificateInboundPort;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CertificateDTOResponse> getAllCertificates() {
        return certificateInboundPort.getAllCertificatesWithDetails();
    }

    @POST
    @Path("/{id}/notes")
    @Consumes(MediaType.APPLICATION_JSON)
    public NoteDTOResponse addNoteToCertificate(@PathParam("id") long certificateId, NoteDTORequest noteDtoRequest) {
        Note note = noteMapper.toDomainFromDTO(noteDtoRequest);
        return certificateInboundPort.addNoteToCertificate(certificateId, note);
    }

    @PUT
    @Path("{id}/editable-properties")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEditableCertificateProperties(@PathParam("id") long certificateId, CertificateUpdateDTO certificateUpdateDTO) {
        // TODO: Consider mapping from dto to domain model if need for more editable properties and/or service logic occurs
        certificateInboundPort.updateEditableCertificateProperties(certificateId, certificateUpdateDTO);
    }
}
