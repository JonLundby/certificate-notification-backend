package org.acme.domain.ports;

import org.acme.outbound.dto.ExpirationNotificationPayloadDto;
import org.acme.outbound.dto.NewCertificateNotificationPayloadDto;

public interface HttpNotificationOutboundPort {
    void sendHttpExpirationNotification(ExpirationNotificationPayloadDto payloadDto);
//    void sendNewCertificateFoundNotification(NewCertificateNotificationPayloadDto payload);
    void sendNewCertificateFoundNotification(NewCertificateNotificationPayloadDto payload);
}
