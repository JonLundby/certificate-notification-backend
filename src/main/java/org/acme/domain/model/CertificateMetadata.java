package org.acme.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

@Entity
public class CertificateMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String type;
    @NotBlank
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

    @OneToMany
    @Column(name = "id_uri")
    private List<UriEntity> uris;

//    @OneToMany
//    @Column(name = "id_note")
//    private List<Note> note;


    public CertificateMetadata() {
    }

    public CertificateMetadata(Long id, String type, String subject, Date dateNotBefore, Date dateNotAfter) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.dateNotBefore = dateNotBefore;
        this.dateNotAfter = dateNotAfter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<UriEntity> getUri() {
        return uri;
    }

    public void setUri(List<UriEntity> uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "CertificateMetadata{" +
                "\n\tid=" + id +
                "\n\ttype='" + type + '\'' +
                "\n\tsubject='" + subject + '\'' +
                "\n\tdateNotBefore=" + dateNotBefore +
                "\n\tdateNotAfter=" + dateNotAfter +
                '}';
    }
}
