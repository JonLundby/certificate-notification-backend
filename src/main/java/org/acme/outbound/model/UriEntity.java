package org.acme.outbound.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class UriEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column(nullable = false, unique = true, length = 2048)
    private String uri;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private CertificateMetadataEntity certificateMetadataEntity;

    public UriEntity() {
    }

    public UriEntity(String uri) {
        this.uri = uri;
    }

    public long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public CertificateMetadataEntity getCertificateMetadataEntity() {
        return certificateMetadataEntity;
    }

    public void setCertificateMetadataEntity(CertificateMetadataEntity certificateMetadataEntity) {
        this.certificateMetadataEntity = certificateMetadataEntity;
    }

    @Override
    public String toString() {
        return "UriEntity{" +
                "uri='" + uri + '\'' +
                '}';
    }
}
