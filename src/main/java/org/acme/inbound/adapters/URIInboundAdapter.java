package org.acme.inbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.dto.UriDTO;
import org.acme.inbound.mapper.UriMapper;
import org.acme.domain.model.UriDomainModel;
import org.acme.domain.ports.UriInboundPort;

import java.util.List;

@ApplicationScoped
@Path("/uris")
public class URIInboundAdapter {

    @Inject
    UriInboundPort uriInboundPort;

    @Inject
    UriMapper uriMapper;

    // Get all URIs
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UriDTO> getAllUris() {
        return uriMapper.toUriDtoList(uriInboundPort.findAll());
    }

    // Manual scan
    @GET
    @Path("/scan")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON) //
    public List<UriDTO> scanUris(@QueryParam("notify") @DefaultValue("false") boolean notify) {
        return uriMapper.toUriDtoList(uriInboundPort.dispatchAllUris(notify));
    }

    // Upload URIs
    @POST
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<UriDTO> receiveURIsStr(String uriStr) {
        List<UriDomainModel> entities = uriInboundPort.createURIs(uriStr);

        if (entities.isEmpty()) {
            throw new BadRequestException("Invalid uri's in request, please review your list of uri's");
        }

        return uriMapper.toUriDtoList(entities);
    }
}
