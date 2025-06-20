package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.HttpNotificationOutboundPort;
import org.acme.outbound.dto.NotificationPayloadDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class HttpNotificationOutboundAdapter implements HttpNotificationOutboundPort {

    private static final Logger logger = Logger.getLogger(HttpNotificationOutboundAdapter.class);

    @Inject
    @RestClient
    NotificationClient notificationClient;

    @Override
    public void sendHttpNotification(CertificateMetadata certMetadata, UriEntity uriEntity) {
        try {

            NotificationPayloadDto payload = new NotificationPayloadDto(
                    uriEntity.getUri(),
                    certMetadata.getDateNotAfter().toString()
            );

            System.out.println("\n---------- SENDING HTTP Notification ----------");
            System.out.println(payload.toString());

            notificationClient.sendNotification(payload);
            logger.info("Notification sent successfully to Mockoon");

        } catch (Exception e) {
            logger.error("Failed to send notification", e);
        }
    }
}
