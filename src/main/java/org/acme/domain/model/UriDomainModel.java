package org.acme.domain.model;

// Do not make JPA annotations here in the domain class!!
public class UriDomainModel {
    private long id;
    private String uri;
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
