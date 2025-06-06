package org.acme.inbound;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.dto.UriEntityDTO;
import org.acme.domain.mapper.UriMapper;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.UriInboundPort;

import java.util.List;

@ApplicationScoped
@Path("/uris")
public class URIInboundAdapter {

    @Inject
    UriInboundPort uriInboundPort;

    @Inject
    UriMapper uriMapper;

    @POST
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<UriEntityDTO> receiveURIsStr(String uriStr) {
        List<UriEntity> entities = uriInboundPort.createURIs(uriStr);

        if (entities.isEmpty()) {
            throw new BadRequestException("Invalid uri's in request, please review your list of uri's");
        }

        return uriMapper.toUriEntityDtoList(entities);
    }
}
