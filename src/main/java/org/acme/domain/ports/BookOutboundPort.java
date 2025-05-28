package org.acme.domain.ports;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.Book;

public interface BookOutboundPort extends PanacheRepository<Book> {
}
