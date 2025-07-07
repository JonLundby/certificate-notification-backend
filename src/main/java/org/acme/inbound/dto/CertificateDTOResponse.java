package org.acme.inbound.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CertificateDTOResponse {
    private Long id;
    private String issuerSerialNumberId;
    private String type;
    private String subject;
    private Date dateNotBefore;
    private Date dateNotAfter;
    private LocalDateTime notifiedAt60Days;
    private LocalDateTime notifiedAt30Days;
    private LocalDateTime notifiedAt14Days;
    private String certificateLocation;
    private String passwordLocation;
    private String privateKeyLocation;
    private List<UriDTO> uris;
    private List<NoteDTO> notes;

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

    public LocalDateTime getNotifiedAt60Days() {
        return notifiedAt60Days;
    }

    public void setNotifiedAt60Days(LocalDateTime notifiedAt60Days) {
        this.notifiedAt60Days = notifiedAt60Days;
    }

    public LocalDateTime getNotifiedAt30Days() {
        return notifiedAt30Days;
    }

    public void setNotifiedAt30Days(LocalDateTime notifiedAt30Days) {
        this.notifiedAt30Days = notifiedAt30Days;
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

    public List<UriDTO> getUris() {
        return uris;
    }

    public void setUris(List<UriDTO> uris) {
        this.uris = uris;
    }

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }
}
