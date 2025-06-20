package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;

import java.time.LocalDate;

public interface HttpNotificationOutboundPort {
    void sendHttpNotification(CertificateMetadata certMetadata, UriEntity uriEntity);
}
