package org.acme.inbound.dto;

import java.time.LocalDateTime;

public class NoteDTORequest {
    private LocalDateTime localDateTimeStamp;
    private String text;

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
}
