package org.acme.outbound.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.outbound.model.NoteEntity;

@ApplicationScoped
public class NoteRepository implements PanacheRepository<NoteEntity> {
}
