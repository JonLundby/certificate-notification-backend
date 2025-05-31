package org.acme.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class UriEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column(nullable = false, unique = true, length = 2048)
    private String uri;

    public UriEntity() {
    }

    public UriEntity(String uri) {
        this.uri = uri;
    }

    public long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "UriEntity{" +
                "uri='" + uri + '\'' +
                '}';
    }
}
