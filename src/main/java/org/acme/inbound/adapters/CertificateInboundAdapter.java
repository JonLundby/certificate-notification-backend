package org.acme.inbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.inbound.dto.CertificateDTOResponse;
import org.acme.inbound.dto.CertificateUpdateDTO;
import org.acme.inbound.dto.NoteDTORequest;
import org.acme.domain.model.Note;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.inbound.dto.NoteDTOResponse;
import org.acme.inbound.mapper.CertificateMetadataMapper;
import org.acme.inbound.mapper.NoteMapper;
import org.jboss.resteasy.reactive.MultipartForm;
import org.jboss.resteasy.reactive.RestForm;

import java.io.InputStream;
import java.util.List;

@ApplicationScoped
@Path("certificates")
public class CertificateInboundAdapter {

    @Inject
    NoteMapper noteMapper;

    @Inject
    CertificateInboundPort certificateInboundPort;

    @Inject
    CertificateMetadataMapper certificateMetadataMapper;

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
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateDTOResponse updateEditableCertificateProperties(@PathParam("id") long certificateId, CertificateUpdateDTO certificateUpdateDTO) {
        return certificateInboundPort.updateEditableCertificateProperties(certificateId, certificateUpdateDTO);
    }

    @POST
    @Path("/upload/client")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public CertificateDTOResponse uploadClientCertificate(@RestForm("file") InputStream fileInputStream) {
        return certificateMetadataMapper.toCertificateDTOResponseFromDomain(certificateInboundPort.uploadClientCertificate(fileInputStream));
    }
}
