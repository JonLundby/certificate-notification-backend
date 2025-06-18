package org.acme.domain.service;

import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;

import java.util.Optional;

@ApplicationScoped
public class NotificationService {

//    @Inject
//    Mailer mailer;

    public void sendCertificationExpirationNotification(Optional<CertificateMetadata> certMetadata, UriEntity uriEntity) {

        System.out.println("-------------------------------------------------");
        System.out.println(uriEntity.getUri());
        System.out.println(certMetadata.toString());
        System.out.println("-------------------------------------------------");
    }
}
