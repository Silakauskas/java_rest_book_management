package com.SS.restapi.helpers;

public class Constants {
    // API constants
    public static final Integer CURRENT_YEAR = 2020;
    public static final Integer ANTIQUE_MOST_RECENT_YEAR = 1900;
    public static final Integer ANTIQUE_TOTAL_PRICE_WEIGHT = 10;
    public static final Integer MIN_SCIENCE_INDEX = 1;
    public static final Integer MAX_SCIENCE_INDEX = 10;

    // Err messages
    public String BookNotFound(Long barcode){
        return "There is no such item with barcode "+barcode+". " +
                "Please provide a barcode of an existing item.";
    }

    public String AntiqueBookNoReleaseYear(){
        return "No release year was specified for antique book. " +
                "Please specify release year (<"+ANTIQUE_MOST_RECENT_YEAR+")" +
                "or set a type of `book`.";
    }

    public String ScienceJournalNoScienceIndex(){
        return "No science index was specified for science journal. " +
                "Please specify science index (>"+MIN_SCIENCE_INDEX+" and <"+MAX_SCIENCE_INDEX+")" +
                "or set a type of `book`.";
    }

    public String AntiqueBookTooRecent(){
        return "Release year is too recent for an antique book (has to be < "+ANTIQUE_MOST_RECENT_YEAR+")."+
                "Please specify a type of `book` or double check the release year.";
    }

    public String ScienceIndexOutOfRange(){
        return "Science index does not fit into defined range (has to be from "+
                ""+MIN_SCIENCE_INDEX+" to "+MAX_SCIENCE_INDEX+")."+
                "Please specify a type of `book` or double check the science index.";
    }

    public String BookTypeNotCorrect(){
        return "No such book type. Has to be one of: book, antique, science journal. Book not added.";
    }

    public String CouldNotUpdateItemAttribute(Long barcode, String key){
        return "Item "+barcode+" attribute "+key+" could not be updated. Please check attribute and book type.";
    }

}
