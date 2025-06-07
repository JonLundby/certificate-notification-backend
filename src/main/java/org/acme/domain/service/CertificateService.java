package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.domain.ports.CertificateInboundPort;
import org.acme.domain.ports.CertificateOutboundPort;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.util.Date;

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

            // Timer for how long to try connecting
            socket.setSoTimeout(10_000);
            socket.startHandshake();


            X509Certificate cert = (X509Certificate) socket.getSession().getPeerCertificates()[0];

            Date dateNotAfter = cert.getNotAfter();
            System.out.println("Not after: " + dateNotAfter);
            System.out.println(cert);
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
