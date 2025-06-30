package org.acme.outbound.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDateTime localDateTimeStamp;
    @Column(length = 1200, nullable = false)
    String text;
    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private CertificateMetadataEntity certificateMetadataEntity;

    public NoteEntity() {
    }

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

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", certificateMetadataEntity=" + certificateMetadataEntity +
                '}';
    }
}
