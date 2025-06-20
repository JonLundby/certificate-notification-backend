package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;

import java.time.LocalDateTime;

public interface MailNotificationOutboundPort {
    void sendMail(CertificateMetadata certMetadata, UriEntity uriEntity, LocalDateTime expiryDate);
}
