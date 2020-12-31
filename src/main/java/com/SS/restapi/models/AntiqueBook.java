package com.SS.restapi.models;

import javax.persistence.*;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "SELECT t FROM Book t")
})
public class AntiqueBook extends Book{
    private static final long serialVersionUID = 1L;

    @Column(name = "releaseYear")
    private Integer releaseYear;

    /* Release year */
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    /* Antique book's total price */
    @Override
    public Double calcTotalPrice() {
        return  getQuantity() * getUnitPrice() * (2020-releaseYear)/10;
    }

    /* Update Antique book's attributes */
    @Override
    public Boolean updateItem(String key, String value) {
        switch(key) {
            case "releaseYear": //special for antique book
                setReleaseYear(Integer.parseInt(value));
                break;
            default:
                return super.updateItem(key, value); // Update main attributes
        }
        return true;
    }
}
