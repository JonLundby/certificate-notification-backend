package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriDomainModel;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.domain.ports.HttpNotificationOutboundPort;
import org.acme.domain.ports.MailNotificationOutboundPort;
import org.acme.outbound.dto.ExpirationNotificationPayloadDto;
import org.acme.outbound.dto.NewCertificateNotificationPayloadDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class NotificationService {

    @Inject
    MailNotificationOutboundPort mailNotificationOutboundPort;

    @Inject
    HttpNotificationOutboundPort httpNotificationOutboundPort;

    @Inject
    CertificateOutboundPort certificateOutboundPort;

    public void sendClientCertificateExpirationNotification(List<CertificateMetadata> certificateMetadataList) {
        UriDomainModel uriDomainModel = new UriDomainModel();
        LocalDateTime now = LocalDateTime.now();

        certificateMetadataList.forEach(clientCertificate -> {
            LocalDateTime expiryDate = clientCertificate.getDateNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            long daysUntilExpiry = ChronoUnit.DAYS.between(now, expiryDate);

            ExpirationNotificationPayloadDto payloadDto = new ExpirationNotificationPayloadDto(
                    uriDomainModel.getUri(),
                    clientCertificate.getDateNotAfter().toString()
            );

            if (daysUntilExpiry <= 60 && daysUntilExpiry > 30 && !clientCertificate.isNotifiedAt60Days()) {

                mailNotificationOutboundPort.sendExpirationMail(clientCertificate, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpExpirationNotification(payloadDto);
                certificateOutboundPort.updateNotifiedAt(clientCertificate, now, 60);

            } else if (daysUntilExpiry <= 30 && daysUntilExpiry > 14 && !clientCertificate.isNotifiedAt30Days()) {

                mailNotificationOutboundPort.sendExpirationMail(clientCertificate, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpExpirationNotification(payloadDto);
                certificateOutboundPort.updateNotifiedAt(clientCertificate, now, 30);

            } else if (daysUntilExpiry <= 14 && !clientCertificate.isNotifiedAt14Days()) {

                mailNotificationOutboundPort.sendExpirationMail(clientCertificate, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpExpirationNotification(payloadDto);
                certificateOutboundPort.updateNotifiedAt(clientCertificate, now, 14);
            }
        });
    }

    public void sendCertificationExpirationNotification(CertificateMetadata certMetadata, UriDomainModel uriDomainModel) {

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiryDate = certMetadata.getDateNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            long daysUntilExpiry = ChronoUnit.DAYS.between(now, expiryDate);

            ExpirationNotificationPayloadDto payloadDto = new ExpirationNotificationPayloadDto(
                    uriDomainModel.getUri(),
                    certMetadata.getDateNotAfter().toString()
            );

            if (daysUntilExpiry <= 60 && daysUntilExpiry > 30 && !certMetadata.isNotifiedAt60Days()) {

                mailNotificationOutboundPort.sendExpirationMail(certMetadata, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpExpirationNotification(payloadDto);
                certificateOutboundPort.updateNotifiedAt(certMetadata, now, 60);

            } else if (daysUntilExpiry <= 30 && daysUntilExpiry > 14 && !certMetadata.isNotifiedAt30Days()) {

                mailNotificationOutboundPort.sendExpirationMail(certMetadata, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpExpirationNotification(payloadDto);
                certificateOutboundPort.updateNotifiedAt(certMetadata, now, 30);

            } else if (daysUntilExpiry <= 14 && !certMetadata.isNotifiedAt14Days()) {

                mailNotificationOutboundPort.sendExpirationMail(certMetadata, uriDomainModel, expiryDate);
                httpNotificationOutboundPort.sendHttpExpirationNotification(payloadDto);
                certificateOutboundPort.updateNotifiedAt(certMetadata, now, 14);
            }
    }

    public void sendNewCertificateFoundNotification(CertificateMetadata savedCertMeta, UriDomainModel uriDomainModel) {
        mailNotificationOutboundPort.sendNewCertificateFoundNotification(savedCertMeta);

        //extracting issuer & serial number for http notification payload dto
        int indexOfHashtagDivider = savedCertMeta.getIssuerSerialNumberId().indexOf("#");
        String issuer = savedCertMeta.getIssuerSerialNumberId().substring(0, indexOfHashtagDivider);
        String serialNumber = savedCertMeta.getIssuerSerialNumberId().substring(indexOfHashtagDivider + 1);

        NewCertificateNotificationPayloadDto httpPayloadDto = new NewCertificateNotificationPayloadDto(
                uriDomainModel.getUri(),
                issuer,
                serialNumber,
                savedCertMeta.getType(),
                savedCertMeta.getSubject(),
                savedCertMeta.getDateNotBefore(),
                savedCertMeta.getDateNotAfter()
        );

        httpNotificationOutboundPort.sendNewCertificateFoundNotification(httpPayloadDto);
    }
}
