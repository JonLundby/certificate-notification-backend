package org.acme.outbound.adapters;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.MailNotificationOutboundPort;

import java.time.LocalDateTime;

@ApplicationScoped
public class MailNotificationOutboundAdapter implements MailNotificationOutboundPort {

    @Inject
    Mailer mailer;

    @Override
    public void sendMail(CertificateMetadata certMetadata, UriEntity uriEntity, LocalDateTime expiryDate) {
        System.out.println("\n---------- SENDING MAIL ----------");
        mailer.send(
                Mail.withText("someone@example.com",
                        "Certificate expiration notification",
                        "Certificate for URI " + uriEntity.getUri() + " is expiring on " + expiryDate + ". Please take action soon.")
        );
    }

}
