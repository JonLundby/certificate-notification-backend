package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriDomainModel;

public interface HttpNotificationOutboundPort {
    void sendHttpNotification(CertificateMetadata certMetadata, UriDomainModel uriDomainModel);
}
