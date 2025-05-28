package org.acme.inbound;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.model.Book;
import org.acme.domain.ports.BookInboundPort;

import java.util.List;

@ApplicationScoped
@Path("/books")
public class BookAdapterInbound {

    @Inject
    BookInboundPort bookInboundPort;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return bookInboundPort.getBooks();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book createBook(Book book) {
        return bookInboundPort.createBook(book);
    }
}
