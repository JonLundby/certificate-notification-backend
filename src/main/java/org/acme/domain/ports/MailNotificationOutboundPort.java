package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;

import java.time.LocalDate;

public interface MailNotificationOutboundPort {
    void sendMail(CertificateMetadata certMetadata, UriEntity uriEntity, LocalDate expiryDate);
}
