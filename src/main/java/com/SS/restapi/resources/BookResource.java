package com.SS.restapi.resources;

import com.SS.restapi.dao.BookDAO;
import com.SS.restapi.models.ScienceJournal;
import com.SS.restapi.models.AntiqueBook;
import com.SS.restapi.models.Book;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Locale;


@RequestScoped
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookDAO bookDAO;

    @GET
    public Response getAll() {
        System.out.print("Getting all books.");
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
    public Response create(String s) {
        System.out.println(s);

        JsonObject message = new Gson().fromJson(s, JsonObject.class);
        String tempBookType = message.get("type").getAsString().toLowerCase();

        if (tempBookType.equals("book")) {
            Book book = new Gson().fromJson(message, Book.class);
            bookDAO.create(book);
            System.out.println("Inserting a usual book.");
        } else if (tempBookType.equals("antique")) {
            AntiqueBook book = new Gson().fromJson(message, AntiqueBook.class);
            bookDAO.create(book);
            System.out.println("Inserting an antique book.");
        } else if (tempBookType.equals("science journal")) {
            ScienceJournal book = new Gson().fromJson(message, ScienceJournal.class);
            bookDAO.create(book);
            System.out.println("Inserting a science journal.");
        }

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
/*
        if (book instanceof ScienceJournal){

        } else if (book instanceof AntiqueBook) {

        } else {

        }
        Double totalPrice = book.getUnitPrice()*book.getQuantity();
*/
        return Response.ok(book.getClass()+" total price "+book.calcTotalPrice()).build();
    }


}