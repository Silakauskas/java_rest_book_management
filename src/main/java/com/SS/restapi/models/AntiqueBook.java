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
}
