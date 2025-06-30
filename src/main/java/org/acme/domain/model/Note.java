package org.acme.domain.model;

import org.acme.outbound.model.CertificateMetadataEntity;

import java.time.LocalDateTime;

public class Note {
    private long id;
    private LocalDateTime localDateTimeStamp;
    private String text;
    private CertificateMetadataEntity certificateMetadataEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTimeStamp() {
        return localDateTimeStamp;
    }

    public void setLocalDateTimeStamp(LocalDateTime localDateTimeStamp) {
        this.localDateTimeStamp = localDateTimeStamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CertificateMetadataEntity getCertificateMetadataEntity() {
        return certificateMetadataEntity;
    }

    public void setCertificateMetadataEntity(CertificateMetadataEntity certificateMetadataEntity) {
        this.certificateMetadataEntity = certificateMetadataEntity;
    }
}
