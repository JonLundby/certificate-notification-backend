package org.acme.domain.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CertificateMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String issuerSerialNumberId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String subject;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateNotBefore;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateNotAfter;

//    private String certificateLocation; // User edited property (filepath?)
//    private String passwordLocation; // User edited property (filepath?)
//    private String privateKeyLocation; // User edited property (filepath?)

    @OneToMany(mappedBy = "certificateMetadata") // no cascading since URIs are long-lived and individual
    private List<UriEntity> uris = new ArrayList<>();

//    @OneToMany
//    @Column(name = "id_note")
//    private List<Note> notes;


    public CertificateMetadata() {
    }


    public Long getId() {
        return id;
    }

    public String getIssuerSerialNumberId() {
        return issuerSerialNumberId;
    }

    public void setIssuerSerialNumberId(String issuerSerialNumberId) {
        this.issuerSerialNumberId = issuerSerialNumberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateNotBefore() {
        return dateNotBefore;
    }

    public void setDateNotBefore(Date dateNotBefore) {
        this.dateNotBefore = dateNotBefore;
    }

    public Date getDateNotAfter() {
        return dateNotAfter;
    }

    public void setDateNotAfter(Date dateNotAfter) {
        this.dateNotAfter = dateNotAfter;
    }

    public List<UriEntity> getUris() {
        return uris;
    }

    public void setUris(List<UriEntity> uris) {
        this.uris = uris;
    }

    @Override
    public String toString() {
        return "CertificateMetadata{" +
                "\n\tid=" + id +
                "\n\tissuerSerialNumberId='" + issuerSerialNumberId + '\'' +
                "\n\ttype='" + type + '\'' +
                "\n\tsubject='" + subject + '\'' +
                "\n\tdateNotBefore=" + dateNotBefore +
                "\n\tdateNotAfter=" + dateNotAfter +
                "\n\turis=" + uris +
                '}';
    }
}
