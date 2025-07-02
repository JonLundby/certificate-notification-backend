package org.acme.inbound.dto;

import java.time.LocalDateTime;

public class NoteDTOResponse {
    private long id;
    private LocalDateTime localDateTimeStamp;
    private String text;
    private long certificateId;

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

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }
}
