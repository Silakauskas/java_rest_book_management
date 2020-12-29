package com.SS.restapi.resources;

import com.SS.restapi.dao.BookDAO;
import com.SS.restapi.models.Book;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookDAO bookDAO;

    @GET
    public Response getAll() {
        return Response.ok(bookDAO.getAll()).build();
    }

    @GET
    @Path("{barcode}")
    public Response getBook(@PathParam("barcode") Long barcode) {
        Book book = bookDAO.findByBarcode(barcode);

        return Response.ok(book).build();
    }

    @PUT
    @Path("{barcode}")
    public Response update(@PathParam("barcode") Long barcode, Book book) {
        Book updateBook = bookDAO.findByBarcode(barcode);

        updateBook.setName(book.getName());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setQuantity(book.getQuantity());
        updateBook.setUnitPrice(book.getUnitPrice());
        bookDAO.update(updateBook);

        return Response.ok().build();
    }

    @POST
    public Response create(Book book) {
        bookDAO.create(book);
        return Response.ok().build();
    }

    @DELETE
    @Path("{barcode}")
    public Response delete(@PathParam("barcode") Long barcode) {
        Book deleteBook = bookDAO.findByBarcode(barcode);

        bookDAO.delete(deleteBook);

        return Response.ok().build();
    }

    @GET
    @Path("/total_price/{barcode}")
    public Response getTotalPrice(@PathParam("barcode") Long barcode) {
        Book book = bookDAO.findByBarcode(barcode);
        Float totalPrice = book.getUnitPrice()*book.getQuantity();

        return Response.ok(totalPrice).build();
    }


}