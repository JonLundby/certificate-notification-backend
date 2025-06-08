package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.domain.ports.CertificateOutboundPort;

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

@ApplicationScoped
public class CertificateService implements CertificateInboundPort {

    @Inject
    CertificateOutboundPort certificateOutboundPort;

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
                             .createSocket(uri.getHost(), uri.getPort() > 0 ? uri.getPort() : 443)) {

            // Timer for how long to try connecting (ms)
            socket.setSoTimeout(10_000);
            socket.startHandshake();

            // Instantiating certificate and populating certificateMetadata
            X509Certificate cert = (X509Certificate) socket.getSession().getPeerCertificates()[0];
            CertificateMetadata certMeta = new CertificateMetadata();

            // Extracting certificate properties
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
                    certMeta.setType("unknown");
                }
            } else {
                certMeta.setType("no extended user key id's");
            }

            // SUBJECT
            // Getting the certificate principal (sort of like a frontpage with common details (CN, OU, O, L, ST & C) about the holder of the certificate)
            X500Principal principal = cert.getSubjectX500Principal();
            String subject = principal.getName();
            certMeta.setSubject(subject);

            // VALID FROM-TO
            Date dateNotAfter = cert.getNotAfter();
            certMeta.setDateNotAfter(dateNotAfter);
            Date dateNotBefore = cert.getNotBefore();
            certMeta.setDateNotBefore(dateNotBefore);


            System.out.println("\n---------- REAL CERT ----------");
            System.out.println(dateNotAfter);
            System.out.println(dateNotBefore);
            System.out.println(ekuOids);
            System.out.println("subject: " + subject);

            System.out.println("\n---------- CERT METADATA ----------");
            System.out.println(certMeta);

            // the error seems to occur here
            certificateOutboundPort.persist(certMeta);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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
