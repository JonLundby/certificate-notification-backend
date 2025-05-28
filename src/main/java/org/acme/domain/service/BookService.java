package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.Book;
import org.acme.domain.ports.BookInboundPort;
import org.acme.domain.ports.BookOutboundPort;

import java.util.List;

@ApplicationScoped
public class BookService implements BookInboundPort {

    @Inject
    BookOutboundPort bookOutboundPort;

    @Override
    public List<Book> getBooks() {
        System.out.println("getting books...");
        return bookOutboundPort.listAll();
    }

    @Override
    public Book createBook(Book book) {
         bookOutboundPort.persist(book);
         return book;
    }
}
