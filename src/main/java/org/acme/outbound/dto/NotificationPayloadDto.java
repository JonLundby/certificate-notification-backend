package org.acme.outbound.dto;

public class NotificationPayloadDto {
    private String uri;
    private String certificateExpires;

    public NotificationPayloadDto() {
    }

    public NotificationPayloadDto(String uri, String certificateExpires) {
        this.uri = uri;
        this.certificateExpires = certificateExpires;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCertificateExpires() {
        return certificateExpires;
    }

    public void setCertificateExpires(String certificateExpires) {
        this.certificateExpires = certificateExpires;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"uri\":" + "\"" + uri + "\",\n" +
                "\t\"certificateExpires\":" + "\"" + certificateExpires + "\"" +
                "\n}";
    }
}
