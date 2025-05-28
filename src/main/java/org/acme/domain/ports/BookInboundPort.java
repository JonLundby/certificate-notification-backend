package org.acme.domain.ports;

import org.acme.domain.model.Book;

import java.util.List;

public interface BookInboundPort {
    List<Book> getBooks();
    Book createBook(Book book);
}
