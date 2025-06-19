package org.acme.outbound.adapters;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.MailNotificationOutboundPort;

import java.time.LocalDate;

@ApplicationScoped
public class MailAdapter implements MailNotificationOutboundPort {

    @Inject
    Mailer mailer;

    @Override
    public void sendMail(CertificateMetadata certMetadata, UriEntity uriEntity, LocalDate expiryDate) {
        mailer.send(
                Mail.withText("someone@example.com",
                        "Certificate expiration notification",
                        "Certificate for URI " + uriEntity.getUri() + " is expiring on " + expiryDate + ". Please take action soon.")
        );
    }

}
