package org.acme.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CertificateMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commonName;

//    @OneToMany
//    @Column(name = "id_uri")
//    private List<UriEntity> uri;


    public CertificateMetadata() {
    }

    public CertificateMetadata(Long id, String commonName) {
        this.id = id;
        this.commonName = commonName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
