package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriDomainModel;

import java.time.LocalDateTime;

public interface MailNotificationOutboundPort {
    void sendMail(CertificateMetadata certMetadata, UriDomainModel uriDomainModel, LocalDateTime expiryDate);
}
