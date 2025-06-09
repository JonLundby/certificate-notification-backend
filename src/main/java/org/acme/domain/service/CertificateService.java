package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.domain.ports.UriOutboundPort;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CertificateService implements CertificateInboundPort {

    @Inject
    CertificateOutboundPort certificateOutboundPort;

    @Inject
    UriOutboundPort uriOutboundPort;

    @Override
    public String getAllCertificates() {
        return "Http GET request called 'getAllCertificates'";
    }

    @Override
    public void retrieveCertificateMetadataDelegator(String uriStr) {
        // TODO: consider making unit test of retrieveCertificateMetadataDelegator and/or the retrieveHTTPSCertificateMetadata etc.

        URI uri;
        try {
            uri = URI.create(uriStr);   // valid uri syntax check
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Malformed URI: " + uriStr, e);
        }

        String scheme = uri.getScheme().toLowerCase();

        switch (scheme) {
            case "https":
                retrieveHTTPSCertificateMetadata(uri);
                break;
            case "ldap":
                retrieveLDAPCertificateMetadata(uri);
                break;
            case "imaps":
                retrieveIMAPSCertificateMetadata(uri);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported scheme: " + scheme);
        }
    }

    private void retrieveHTTPSCertificateMetadata(URI uri) {

        if (!"https".equalsIgnoreCase(uri.getScheme())) {
            throw new BadRequestException("Only HTTPS supported");
        }

        // java 7 - try with resources statement which automatically closes resource/connection
        // casting to (SSLSocket) because the factory returns a Socket class and SSLSocket is a subclass of Socket
        try (SSLSocket socket =
                     (SSLSocket) SSLSocketFactory.getDefault()
                             .createSocket(uri.getHost(), uri.getPort() > 0 ? uri.getPort() : 443)) { // TODO: consider only having 443 as possible port!!

            // Timer for how long to try connecting (ms)
            socket.setSoTimeout(10_000);
            socket.startHandshake();

            // Instantiating certificate and populating certificateMetadata
            X509Certificate cert = (X509Certificate) socket.getSession().getPeerCertificates()[0];
            CertificateMetadata certMeta = new CertificateMetadata();

            System.out.println(cert.getNotAfter());

            // Extracting certificate properties
            // COMPOSITE KEY - issuer + serialNumber
            X500Principal issuer = cert.getIssuerX500Principal();
            String serialNumber = cert.getSerialNumber().toString();
            certMeta.setIssuerSerialNumberId(issuer.getName() + "#" + serialNumber);

            // EXTENDED KEY USAGE - identifies whether it is a client or server certificate or both
            List<String> ekuOids = cert.getExtendedKeyUsage();
            if (ekuOids != null) {
                boolean isServer = ekuOids.contains("1.3.6.1.5.5.7.3.1");
                boolean isClient = ekuOids.contains("1.3.6.1.5.5.7.3.2");

                if (isServer && isClient) {
                    certMeta.setType("Both");
                } else if (isServer) {
                    certMeta.setType("Server");
                } else if (isClient) {
                    certMeta.setType("Client");
                } else {
                    certMeta.setType("Unknown");
                }
            } else {
                certMeta.setType("No extended key usage id's were found");
            }

            // SUBJECT
            // Getting the certificate principal (sort of like a page with common details (CN, OU, O, L, ST & C) about the holder of the certificate)
            X500Principal principal = cert.getSubjectX500Principal();
            String subject = principal.getName();
            certMeta.setSubject(subject);

            // VALID FROM-TO
            Date dateNotAfter = cert.getNotAfter();
            certMeta.setDateNotAfter(dateNotAfter);
            Date dateNotBefore = cert.getNotBefore();
            certMeta.setDateNotBefore(dateNotBefore);

            // Check if the certificate already exists in DB
            Optional<CertificateMetadata> existingCert =
                    certificateOutboundPort.findByIssuerSerialNumberId(certMeta.getIssuerSerialNumberId());

            // If certificate metadata already exists then don't save it but set current URI in relation to it
            // Else save the certificate - if URI has a Certificate_id then remove it and set the URI in relation to the newly found cert metadata
            UriEntity uriEntity = uriOutboundPort.findByUri(uri.toString()); // getting a jpa transactional 'managed' entity where changes are tracked and persisted when transactional method ends
            if (existingCert.isPresent()) {
                // omit saving the certification metadata since it already exists and...
                // update the uri to have its certificate_id relate to the already existing certificate metadata
                uriEntity.setCertificateMetadata(existingCert.get());
            } else {
                // Save newly retrieved certificate metadata
                certificateOutboundPort.persist(certMeta);
                // Set the current URI in relation to the newly saved certificate metadata
                uriEntity.setCertificateMetadata(certMeta);
            }

            // TODO: consider making a NoValidCertificateFoundException or SSLHandshakeException
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateParsingException e) {
            throw new RuntimeException(e);
        }
    }

    private void retrieveLDAPCertificateMetadata(URI uri) {

        String uriStr = uri.toString();
        System.out.println("retrieve at: " + uriStr);
    }

    private void retrieveIMAPSCertificateMetadata(URI uri) {
        String uriStr = uri.toString();
        System.out.println("retrieve at: " + uriStr);

    }

}
