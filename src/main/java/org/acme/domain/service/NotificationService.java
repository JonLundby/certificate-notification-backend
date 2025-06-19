package org.acme.domain.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.MailNotificationOutboundPort;
import org.acme.domain.ports.UriOutboundPort;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@ApplicationScoped
public class NotificationService {

    @Inject
    MailNotificationOutboundPort mailNotificationOutboundPort;

    @Inject
    UriOutboundPort uriOutboundPort;

    public void sendCertificationExpirationNotification(Optional<CertificateMetadata> certMetadata, UriEntity uriEntity) {

        certMetadata.ifPresent(cert -> {
            LocalDate now = LocalDate.now();
            LocalDate expiryDate = cert.getDateNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long daysUntilExpiry = ChronoUnit.DAYS.between(now, expiryDate);

            if ( daysUntilExpiry <= 60 && daysUntilExpiry > 30 && !cert.isNotifiedAt60Days()) {
                mailNotificationOutboundPort.sendMail(cert, uriEntity, expiryDate);
                cert.setNotifiedAt60Days(now);
            } else if (daysUntilExpiry <= 30 && daysUntilExpiry > 14 && !cert.isNotifiedAt30Days()) {
                mailNotificationOutboundPort.sendMail(cert, uriEntity, expiryDate);
                cert.setNotifiedAt30Days(now);
            } else if (daysUntilExpiry <= 14 && !cert.isNotifiedAt14Days()) {
                mailNotificationOutboundPort.sendMail(cert, uriEntity, expiryDate);
                cert.setNotifiedAt14Days(now);
            }
        });
    }
}
