package org.acme.outbound.adapters;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.outbound.dto.NotificationPayloadDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

// This RegisterRestClient is a MicroProfile REST client and tells Quarkus that the interface is meant to call an external HTTP service
@RegisterRestClient(configKey = "notification-client") // configKey refers to application.properties where the client address/endpoint is configured
@Path("/certapp")
public interface NotificationClient {

    @POST
    @Path("/notify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void sendNotification(NotificationPayloadDto payload);
}
