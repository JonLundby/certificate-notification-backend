package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriDomainModel;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.domain.ports.HttpNotificationOutboundPort;
import org.acme.domain.ports.MailNotificationOutboundPort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@ApplicationScoped
public class NotificationService {

    @Inject
    MailNotificationOutboundPort mailNotificationOutboundPort;

    @Inject
    HttpNotificationOutboundPort httpNotificationOutboundPort;

    @Inject
    CertificateOutboundPort certificateOutboundPort;

    public void sendCertificationExpirationNotification(Optional<CertificateMetadata> certMetadata, UriDomainModel uriDomainModel) {

        certMetadata.ifPresent(cert -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiryDate = cert.getDateNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            long daysUntilExpiry = ChronoUnit.DAYS.between(now, expiryDate);

            if (daysUntilExpiry <= 60 && daysUntilExpiry > 30 && !cert.isNotifiedAt60Days()) {
                mailNotificationOutboundPort.sendMail(cert, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpNotification(cert, uriDomainModel);
                certificateOutboundPort.updateNotifiedAt(cert, now, 60);
            } else if (daysUntilExpiry <= 30 && daysUntilExpiry > 14 && !cert.isNotifiedAt30Days()) {
                mailNotificationOutboundPort.sendMail(cert, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpNotification(cert, uriDomainModel);
                cert.setNotifiedAt30Days(now);
            } else if (daysUntilExpiry <= 14 && !cert.isNotifiedAt14Days()) {
                mailNotificationOutboundPort.sendMail(cert, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpNotification(cert, uriDomainModel);
                cert.setNotifiedAt14Days(now);
            }
        });
    }
}
