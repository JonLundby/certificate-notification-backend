package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.domain.model.CertificateMetadata;
import org.acme.domain.model.UriEntity;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.domain.ports.UriOutboundPort;

import javax.net.ssl.*;
import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.SecureRandom;
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
        int port;

        switch (scheme) {
            case "https":
                port = 443;
                retrieveCertificateViaTLS(uri, port);
                break;
            case "ldaps":
                port = 636;
                retrieveCertificateViaTLS(uri, port);
                break;
            case "imaps":
                port = 993;
//                retrieveIMAPSCertificate(uri, port);
                retrieveCertificateViaTLS(uri, port);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported scheme: " + scheme);
        }
    }

    // Certificate retrieval for LDAPS & HTTPS
    private void retrieveCertificateViaTLS(URI uri, int port) {
        // Try with resources statement which automatically closes resource/connection
        // casting to (SSLSocket) because the factory returns a Socket class and SSLSocket is a subclass of Socket
        try {
            SSLSocketFactory sslSocketFactory = createTrustAllSSLSocketFactory();

            try (SSLSocket socket =
                     (SSLSocket) sslSocketFactory.createSocket(uri.getHost(), uri.getPort() > 0 ? uri.getPort() : port)) {

                // Timer for how long to try connecting (ms)
                socket.setSoTimeout(10_000);
                socket.startHandshake(); // startHandshake only allows TLS 1.2 & 1.3

                // Instantiating certificate and populating certificateMetadata
                X509Certificate cert = (X509Certificate) socket.getSession().getPeerCertificates()[0];

                // Parse the retrieved cert to certMetadata
                CertificateMetadata certMeta = parseCertificateToCertificateMetadata(cert);

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
            }
        // TODO: consider making a NoValidCertificateFoundException or SSLHandshakeException
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateParsingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // !!! *** !!! *** !!! NOTE THAT THIS IS PARTIALLY INSECURE !!! *** !!! *** !!!
    // Method that creates a type SSLSocketFactory that accepts all certificates
    private SSLSocketFactory createTrustAllSSLSocketFactory() throws Exception {
        // custom array of TrustManagers
        TrustManager[] trustAllCerts = new TrustManager[]{
                // new X509TrustManager with overridden methods
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        // SSLSocketFactory uses a X509Certificate array with trusted certificates from JVM's truststore but...
                        // ... here an empty array of X509Certificates are returned which will be interpreted as not caring about trusted CA's
                        return new X509Certificate[0];
                    }

                    // following two methods normally checks to validate the trusted CA's in the TrustManager but are here overridden with no functionality...
                    // ... so an empty TrustManager array is checked for nothing
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Trust all client certs
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Trust all server certs
                    }
                }
        };

        // Creating a sslContext variable with TLS 1.2 & 1.3 as desired connection type (only in java 11+)
        SSLContext sslContext = SSLContext.getInstance("TLS");
        // initializing the ssl context with the empty and overridden TrustManager array trustAllCerts
        sslContext.init(null, trustAllCerts, new SecureRandom());
        // returning a SocketFactory with the created sslContext content
        return sslContext.getSocketFactory();
    }

    private CertificateMetadata parseCertificateToCertificateMetadata(X509Certificate cert) throws CertificateParsingException {
        CertificateMetadata certMeta = new CertificateMetadata();

        // Extracting certificate properties
        // COMPOSITE KEY = issuer + serialNumber
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

        return certMeta;
    }

}
