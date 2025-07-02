package org.acme.inbound.dto;

public class CertificateUpdateDTO {
    private String certificateLocation; // User edited property (filepath?)
    private String passwordLocation; // User edited property (filepath?)
    private String privateKeyLocation; // User edited property (filepath?)

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
}
