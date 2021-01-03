package com.SS.restapi.resources;

import com.SS.restapi.dao.BookDAO;
import com.SS.restapi.helpers.Errors;
import com.SS.restapi.helpers.PATCH;
import com.SS.restapi.models.ScienceJournal;
import com.SS.restapi.models.AntiqueBook;
import com.SS.restapi.models.Book;
import com.SS.restapi.helpers.Constants;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


@RequestScoped
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookDAO bookDAO;

    // Get all books from DB
    @GET
    public Response getAll() {
        System.out.print("Getting all books.");
        return Response.ok(bookDAO.getAll()).build();
    }

    // Get a specific book
    @GET
    @Path("{barcode}")
    public Response getBook(@PathParam("barcode") Long barcode) {
        Book book = bookDAO.findByBarcode(barcode);

        // Could not find book
        if (book == null){
            return Errors.bookNotFound(barcode);
        }

        return Response.ok(book).build();
    }

    @PATCH
    @Path("{barcode}")
    public Response update(@PathParam("barcode") Long barcode, String s) {

        // Get message in Json Object from POST request body
        JsonObject reqMessage = new Gson().fromJson(s, JsonObject.class);

        // Book to be updated found in DB table - already of type Book, AntiqueBook or ScienceJournal
        Book updateBook = bookDAO.findByBarcode(barcode);

        // Could not find a book to update
        if (updateBook == null){
            return Errors.bookNotFound(barcode);
        }

        // Go through all attributes to be updated
        for (String key : reqMessage.keySet()) {
            // If updating release year - check if it is valid
            if (key.equals("releaseYear") && reqMessage.get(key).getAsInt() > Constants.ANTIQUE_MOST_RECENT_YEAR) {
                return Errors.antiqueBookTooRecent();
            }

            // If updating science index - check if it is valid
            if (key.equals("scienceIndex") && (reqMessage.get(key).getAsInt() < Constants.MIN_SCIENCE_INDEX
                    || reqMessage.get(key).getAsInt() > Constants.MAX_SCIENCE_INDEX)) {
                return Errors.scienceIndexOutOfRange();
            }

            // Could not update an attribute
            if (!updateBook.updateItem(key, reqMessage.get(key).getAsString())) {
                return Errors.couldNotUpdateItemAttribute(barcode, key);
            }
        }
        bookDAO.update(updateBook);
        return Response.ok().build();
    }

    @POST
    public Response create(String s) {

        // Get message in Json Object from POST request body
        JsonObject message = new Gson().fromJson(s, JsonObject.class);

        if (message.get("type") == null || message.get("type").getAsString().isEmpty()) {
            // There is no such book type
            return Errors.bookTypeNotCorrect();
        }

        String tempBookType = message.get("type").getAsString().toLowerCase(); // Provided by user

        // Determine which type of book should be inserted
        switch (tempBookType) {
            case "book": {

                Book book = new Gson().fromJson(message, Book.class);
                bookDAO.create(book);

                break;
            }
            case "antique":
            case "antique book": {

                // Check if the book is valid
                if (message.get("releaseYear") == null) {
                    return Errors.antiqueBookNoReleaseYear();
                }
                if (message.get("releaseYear").getAsInt() > Constants.ANTIQUE_MOST_RECENT_YEAR) {
                    return Errors.antiqueBookTooRecent();
                }
                // Form an antique book object out of JsonObject
                AntiqueBook book = new Gson().fromJson(message, AntiqueBook.class);
                bookDAO.create(book);

                break;
            }
            case "science journal": {

                // Check if the book is valid
                if (message.get("scienceIndex") == null) {
                    return Errors.scienceJournalNoScienceIndex();
                }
                if (message.get("scienceIndex").getAsInt() < Constants.MIN_SCIENCE_INDEX
                        || message.get("scienceIndex").getAsInt() > Constants.MAX_SCIENCE_INDEX) {
                    return Errors.scienceIndexOutOfRange();
                }
                // Form an antique book object out of JsonObject
                ScienceJournal book = new Gson().fromJson(message, ScienceJournal.class);
                bookDAO.create(book);

                break;
            }
            default:
                // There is no such book type
                return Errors.bookTypeNotCorrect();
        }

        // Everything should be correct
        return Response.ok().build();
    }



    @DELETE
    @Path("{barcode}")
    public Response delete(@PathParam("barcode") Long barcode) {

        Book deleteBook = bookDAO.findByBarcode(barcode);

        // Could not find book to delete
        if (deleteBook == null){
            return Errors.bookNotFound(barcode);
        }

        bookDAO.delete(deleteBook);

        return Response.ok().build();
    }


    @GET
    @Path("/total_price/{barcode}")
    public Response getTotalPrice(@PathParam("barcode") Long barcode) {

        Book book = bookDAO.findByBarcode(barcode);

        if (book == null){
            return Errors.bookNotFound(barcode);
        }

        String respMessage = "Book id "+barcode+" Total price:"+String.format("%.2f",book.calcTotalPrice());

        return Response.status(Response.Status.OK).entity(respMessage).build();
    }


}