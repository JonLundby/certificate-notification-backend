package org.acme.inbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.ports.UriOutboundPort;
import org.acme.inbound.dto.UriDTOResponse;
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
    public List<UriDTOResponse> getAllUris() {
        return uriMapper.toUriDTOResponseList(uriInboundPort.findAll());
    }

    // Manual scan
    @GET
    @Path("/scan")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON) //
    public List<UriDTOResponse> scanUris(@QueryParam("notify") @DefaultValue("false") boolean notify) {
        List<UriDomainModel> allUris = uriInboundPort.findAll();
        return uriMapper.toUriDTOResponseList(uriInboundPort.dispatchAllUris(allUris, notify));
    }

    // Upload URIs
    @POST
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<UriDTOResponse> receiveURIsStr(String uriStr) {
        List<UriDomainModel> entities = uriInboundPort.createURIs(uriStr);

        if (entities.isEmpty()) {
            throw new BadRequestException("Invalid uri's in request, please review your list of uri's");
        }

        return uriMapper.toUriDTOResponseList(entities);
    }
}
