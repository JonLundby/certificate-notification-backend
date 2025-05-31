package org.acme.domain.dto;

public class UriEntityDTO {
    private String uri;

    public UriEntityDTO() {
    }

    public UriEntityDTO(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
