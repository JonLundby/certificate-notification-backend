package org.acme.domain.dto;

public class UriEntityDTO {
    private Long id;
    private String uri;

    public UriEntityDTO() {
    }

    public UriEntityDTO(Long id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
