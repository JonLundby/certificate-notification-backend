package org.acme.inbound;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.ports.URIInboundPort;

@ApplicationScoped
@Path("/uris")
public class URIAdapterInbound {

    @Inject
    URIInboundPort uriInboundPort;

    @POST
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public void receiveURIsStr(String uriStr) {
        uriInboundPort.createURIs(uriStr);
    }
}
