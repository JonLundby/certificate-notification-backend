package org.acme.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Do not make JPA annotations here in the domain class!!
public class CertificateMetadata {
    private Long id;
    private String issuerSerialNumberId;
    private String type;
    private String subject;
    private Date dateNotBefore;
    private Date dateNotAfter;
    private LocalDateTime notifiedAt60Days;
    private LocalDateTime notifiedAt30Days;
    private LocalDateTime notifiedAt14Days;
    // ---------- EDITABLE PROPERTIES ---------- \\
    private String certificateLocation; // User edited property (filepath?)
    private String passwordLocation; // User edited property (filepath?)
    private String privateKeyLocation; // User edited property (filepath?)
    // ---------- -------------------- ---------- \\
    private List<UriDomainModel> uris = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();


    public CertificateMetadata() {
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

    public String getCertificateLocation() {
        return certificateLocation;
    }

    public void setCertificateLocation(String certificateLocation) {
        this.certificateLocation = certificateLocation;
    }

    public String getPasswordLocation() {
        return passwordLocation;
    }

    public void setPasswordLocation(String passwordLocation) {
        this.passwordLocation = passwordLocation;
    }

    public String getPrivateKeyLocation() {
        return privateKeyLocation;
    }

    public void setPrivateKeyLocation(String privateKeyLocation) {
        this.privateKeyLocation = privateKeyLocation;
    }

    public List<UriDomainModel> getUris() {
        return uris;
    }

    public void setUris(List<UriDomainModel> uris) {
        this.uris = uris;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
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
