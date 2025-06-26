package org.acme.domain.service;

import jakarta.ws.rs.BadRequestException;
import org.acme.domain.ports.CertificateOutboundPort;
import org.acme.domain.ports.UriOutboundPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {
    @InjectMocks
    CertificateService certificateService;

    @Mock
    CertificateOutboundPort certificateOutboundPort;

    @Mock
    UriOutboundPort uriOutboundPort;

    @Test
    void testRetrieveCertificateMetadataDelegator_https_shouldInvokeTLSRetrieval() {
        // Arrange
        int port = 443;
        String uriStr = "https://example.com";
        URI uri = URI.create(uriStr);

        // SpyService monitors the internal method calls
        CertificateService spyService = Mockito.spy(certificateService);

        // Act - don't make actual TLS handshake during the test
        doNothing().when(spyService).retrieveCertificateViaTLS(uri, port, false);

        // Act - spyService calls the retrieveCertificateMetadataDelegator with uri string
        spyService.retrieveCertificateMetadataDelegator(uriStr, false);

        // Assert - verify that spyService invoked a call to retrieveCertificateViaTLS after calling the delegator above
        verify(spyService).retrieveCertificateViaTLS(uri, port, false);
    }

    // Test that malformed uri throws BadRequestException
    @Test
    void testRetrieveCertificateMetadataDelegator_invalidUri_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> {
            certificateService.retrieveCertificateMetadataDelegator("not a uri", false);
        });
    }

    // Test that uri scheme throws UnsupportedOperationException
    @Test
    void testRetrieveCertificateMetadataDelegator_unknownScheme_shouldThrow() {
        assertThrows(UnsupportedOperationException.class, () -> {
            certificateService.retrieveCertificateMetadataDelegator("ftp://example.com", false);
        });
    }
}