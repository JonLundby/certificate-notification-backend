package org.acme.domain.ports;

import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriDomainModel;

import java.time.LocalDateTime;

public interface MailNotificationOutboundPort {
    void sendExpirationMail(CertificateMetadata certMetadata, UriDomainModel uriDomainModel, LocalDateTime expiryDate);
    void sendNewCertificateFoundNotification(CertificateMetadata certificateMetadata);
}
