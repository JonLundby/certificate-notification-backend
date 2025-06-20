package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;

public interface HttpNotificationOutboundPort {
    void sendHttpNotification(CertificateMetadata certMetadata, UriEntity uriEntity);
}
