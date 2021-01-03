package com.SS.restapi.helpers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


public class Errors {
    private static Response buildStatus(Status status, String message) {
        return Response.status(status).entity(message).build();
    }

    public static Response bookNotFound(Long barcode){
        String message = "There is no such item with barcode " + barcode + ". " +
                "Please provide a barcode of an existing item.";
        return buildStatus(Status.NOT_FOUND, message);
    }

    public static Response antiqueBookTooRecent(){
        String message =  "Release year is too recent for an antique book (has to be < " + Constants.ANTIQUE_MOST_RECENT_YEAR + ")."+
                "Please specify a type of `book` or double check the release year.";
        return buildStatus(Status.BAD_REQUEST, message);
    }

    public static Response bookTypeNotCorrect(){
        String message = "No such book type. Has to be one of: book, antique, science journal. Book not added.";
        return buildStatus(Status.BAD_REQUEST, message);
    }

    public static Response antiqueBookNoReleaseYear(){
        String message = "No release year was specified for antique book. " +
                "Please specify release year (<"+Constants.ANTIQUE_MOST_RECENT_YEAR+")" +
                "or set a type of `book`.";
        return buildStatus(Status.BAD_REQUEST, message);
    }

    public static Response scienceJournalNoScienceIndex(){
        String message = "No science index was specified for science journal. " +
                "Please specify science index (>"+Constants.MIN_SCIENCE_INDEX+" " +
                "and <"+ Constants.MAX_SCIENCE_INDEX+")" +
                "or set a type of `book`.";
        return buildStatus(Status.BAD_REQUEST, message);
    }

    public static Response scienceIndexOutOfRange(){
        String message = "Science index does not fit into defined range (has to be from "+
                ""+Constants.MIN_SCIENCE_INDEX+" to "+Constants.MAX_SCIENCE_INDEX+")."+
                "Please specify a type of `book` or double check the science index.";
        return buildStatus(Status.BAD_REQUEST, message);
    }

    public static Response couldNotUpdateItemAttribute(Long barcode, String key){
        String message = "Item "+barcode+" attribute "+key+" could not be updated. Please check attribute and book type.";
        return buildStatus(Status.BAD_REQUEST, message);
    }

}