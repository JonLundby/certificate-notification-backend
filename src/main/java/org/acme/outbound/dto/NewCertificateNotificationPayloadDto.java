package org.acme.outbound.dto;

import org.acme.domain.model.CertificateMetadata;

import java.time.LocalDateTime;
import java.util.Date;

public class NewCertificateNotificationPayloadDto {
    private String uri;
    private String issuer;
    private String serialNumber;
    private String type;
    private String subject;
    private Date dateNotBefore;
    private Date dateNotAfter;

    public NewCertificateNotificationPayloadDto() {
    }

    public NewCertificateNotificationPayloadDto(String uri, String issuer, String serialNumber, String type, String subject, Date dateNotBefore, Date dateNotAfter) {
        this.uri = uri;
        this.issuer = issuer;
        this.serialNumber = serialNumber;
        this.type = type;
        this.subject = subject;
        this.dateNotBefore = dateNotBefore;
        this.dateNotAfter = dateNotAfter;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    @Override
    public String toString() {
        return "{\n" +
                "  \"uri\": \"" + uri + "\",\n" +
                "  \"issuer\": \"" + issuer + "\",\n" +
                "  \"serialNumber\": \"" + serialNumber + "\",\n" +
                "  \"type\": \"" + type + "\",\n" +
                "  \"subject\": \"" + subject + "\",\n" +
                "  \"dateNotBefore\": \"" + dateNotBefore + "\",\n" +
                "  \"dateNotAfter\": \"" + dateNotAfter + "\",\n" +
                "}";
    }
}
