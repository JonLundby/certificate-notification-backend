package org.acme.outbound.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CertificateMetadataEntity {
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
    private LocalDateTime notifiedAt60Days;
    private LocalDateTime notifiedAt30Days;
    private LocalDateTime notifiedAt14Days;

//    private String certificateLocation; // User edited property (filepath?)
//    private String passwordLocation; // User edited property (filepath?)
//    private String privateKeyLocation; // User edited property (filepath?)

    @OneToMany(mappedBy = "certificateMetadataEntity") // no cascading since URIs are long-lived and individual
    private List<UriEntity> uris = new ArrayList<>();

//    @OneToMany
//    @Column(name = "id_note")
//    private List<Note> notes;


    public CertificateMetadataEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isNotifiedAt60Days() {
        return notifiedAt60Days != null;
    }

    public LocalDateTime getNotifiedAt60Days() {
        return notifiedAt60Days;
    }

    public void setNotifiedAt60Days(LocalDateTime notifiedAt60Days) {
        this.notifiedAt60Days = notifiedAt60Days;
    }

    public boolean isNotifiedAt30Days() {
        return notifiedAt30Days != null;
    }

    public LocalDateTime getNotifiedAt30Days() {
        return notifiedAt30Days;
    }

    public void setNotifiedAt30Days(LocalDateTime notifiedAt30Days) {
        this.notifiedAt30Days = notifiedAt30Days;
    }

    public boolean isNotifiedAt14Days() {
        return notifiedAt14Days != null;
    }

    public LocalDateTime getNotifiedAt14Days() {
        return notifiedAt14Days;
    }

    public void setNotifiedAt14Days(LocalDateTime notifiedAt14Days) {
        this.notifiedAt14Days = notifiedAt14Days;
    }

    public List<UriEntity> getUris() {
        return uris;
    }

    public void setUris(List<UriEntity> uris) {
        this.uris = uris;
    }

    @Override
    public String toString() {
        return "CertificateMetadataEntity{" +
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
