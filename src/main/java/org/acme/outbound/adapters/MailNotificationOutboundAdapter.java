package org.acme.outbound.adapters;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriDomainModel;
import org.acme.domain.ports.MailNotificationOutboundPort;

import java.time.LocalDateTime;

@ApplicationScoped
public class MailNotificationOutboundAdapter implements MailNotificationOutboundPort {

    @Inject
    Mailer mailer; // No bean matches the injection point... but it works?

    @Override
    public void sendExpirationMail(CertificateMetadata certMetadata, UriDomainModel uriDomainModel, LocalDateTime expiryDate) {
        System.out.println("\n---------- SENDING MAIL ----------");
        mailer.send(
                Mail.withText("someone@example.com",
                        "Certificate expiration notification",
                        "Certificate for URI " + uriDomainModel.getUri() + " is expiring on " + expiryDate + ". Please take action soon.")
        );
    }

    @Override
    public void sendNewCertificateFoundNotification(CertificateMetadata certificateMetadata) {
        int hashtagDividerIndex = certificateMetadata.getIssuerSerialNumberId().indexOf("#");
        String issuer = certificateMetadata.getIssuerSerialNumberId().substring(0, hashtagDividerIndex);
        String serialNumber = certificateMetadata.getIssuerSerialNumberId().substring(hashtagDividerIndex +1);

        System.out.println("\n---------- SENDING MAIL ----------");
        // use "List.of("user1@example.com", "user2@example.com")" to send to a list of emails
        mailer.send(
                Mail.withText("someone@example.com",
                        "New certificate found",
                        "New certificate found \nIssuer: " + issuer +"\nserial number: " + serialNumber)
        );
    }
}
