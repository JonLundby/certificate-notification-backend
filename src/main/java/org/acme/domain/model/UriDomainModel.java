package org.acme.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.acme.outbound.model.CertificateMetadataEntity;

// Do not make JPA annotations here in the domain class!!
//@Entity
public class UriDomainModel {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @NotBlank
//    @Column(nullable = false, unique = true, length = 2048)
    private String uri;

//    @ManyToOne
//    @JoinColumn(name = "certificate_id")
    private CertificateMetadata certificateMetadata;

    public UriDomainModel() {
    }

    public UriDomainModel(String uri) {
        this.uri = uri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public CertificateMetadata getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(CertificateMetadata certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }

    @Override
    public String toString() {
        return "UriDomainModel{" +
                "uri='" + uri + '\'' +
                '}';
    }
}
