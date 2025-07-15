package org.acme.outbound.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.ports.HttpNotificationOutboundPort;
import org.acme.outbound.dto.ExpirationNotificationPayloadDto;
import org.acme.outbound.dto.NewCertificateNotificationPayloadDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class HttpNotificationOutboundAdapter implements HttpNotificationOutboundPort {

    private static final Logger logger = Logger.getLogger(HttpNotificationOutboundAdapter.class);

    @Inject
    @RestClient
    NotificationClient notificationClient;

    @Override
    public void sendHttpExpirationNotification(ExpirationNotificationPayloadDto payloadDto) {
        try {
            System.out.println("\n---------- SENDING HTTP Notification ----------");
            System.out.println(payloadDto);

            notificationClient.sendExpirationNotification(payloadDto);
            logger.info("Notification sent successfully to Mockoon");

        } catch (Exception e) {
            logger.error("Failed to send notification", e);
        }
    }

    @Override
    public void sendNewCertificateFoundNotification(NewCertificateNotificationPayloadDto payload) {
        notificationClient.sendNewCertificateNotification(payload);
    }
}
