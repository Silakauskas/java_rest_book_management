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

import java.util.Iterator;
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

        if (book == null){
            String respMessage = "There is no such item with barcode "+barcode+". Please provide a barcode of an existing item.";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respMessage).build();
        }

        return Response.ok(book).build();
    }

    @PUT
    @Path("{barcode}")
    public Response update(@PathParam("barcode") Long barcode, String s) {

        // Get message in Json Object from POST request body
        JsonObject reqMessage = new Gson().fromJson(s, JsonObject.class);

        // Book to be updated found in DB table - already of type Book, AntiqueBook or ScienceJournal
        Book updateBook = bookDAO.findByBarcode(barcode);

        if (updateBook == null){
            String respMessage = "There is no such item with barcode "+barcode+". Please provide a barcode of an existing item.";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respMessage).build();
        }

        Iterator<String> keys = reqMessage.keySet().iterator();
        while (keys.hasNext()){
            String key = keys.next();
            if (!updateBook.updateItem(key, reqMessage.get(key).getAsString())) {
                String respMessage = "Item "+barcode+" attribute "+key+" could not be updated. Please check attribute and book type.";
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respMessage).build();
            };
        }

        bookDAO.update(updateBook);

        return Response.ok().build();
    }

    @POST
    public Response create(String s) {
        System.out.println(s);

        // Get message in Json Object from POST request body
        JsonObject message = new Gson().fromJson(s, JsonObject.class);
        String tempBookType = message.get("type").getAsString().toLowerCase(); // Provided by user

        // Determine which type of book should be inserted
        if (tempBookType.equals("book")) {
            Book book = new Gson().fromJson(message, Book.class);
            bookDAO.create(book);
            System.out.println("Inserting a usual book.");
        } else if (tempBookType.equals("antique")) {
            AntiqueBook book = new Gson().fromJson(message, AntiqueBook.class);
            /* TODO: try to insert book of type ScienceJournal without scienceIndex and with antique book....
            if (book.getReleaseYear > 1900){
               // ... do something ...
               // ... say something ...
            }
             */
            bookDAO.create(book);
            System.out.println("Inserting an antique book.");
        } else if (tempBookType.equals("science journal")) {
            ScienceJournal book = new Gson().fromJson(message, ScienceJournal.class);
            /*
            if (book.getScienceIndex > 10 || book.getScienceIndex < 1){
               // ... do something ...
               // ... say something ...
            }
             */
            bookDAO.create(book);
            System.out.println("Inserting a science journal.");
        } else {
            String respMessage = "No such book type. Has to be one of: book, antique, science journal. Book not added.";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respMessage).build();
        }

        return Response.ok().build();
    }



    @DELETE
    @Path("{barcode}")
    public Response delete(@PathParam("barcode") Long barcode) {
        Book deleteBook = bookDAO.findByBarcode(barcode);

        if (deleteBook == null){
            String respMessage = "There is no such item with barcode "+barcode+". Please provide a barcode of an existing item.";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respMessage).build();
        }

        bookDAO.delete(deleteBook);

        return Response.ok().build();
    }

    @GET
    @Path("/total_price/{barcode}")
    public Response getTotalPrice(@PathParam("barcode") Long barcode) {

        Book book = bookDAO.findByBarcode(barcode);

        if (book == null){
            String respMessage = "There is no such item with barcode "+barcode+". Please provide a barcode of an existing item.";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respMessage).build();
        }

        //String respMessage = "Type of book:"+book.getType()+" (id "+barcode+")   Total price:"+book.calcTotalPrice();
        String respMessage = "Class of book:"+book.getClass()+" (id "+barcode+")   Total price:"+book.calcTotalPrice();
        return Response.status(Response.Status.OK).entity(respMessage).build();
    }


}