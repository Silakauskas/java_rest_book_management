package com.SS.restapi.models;

import javax.persistence.*;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "SELECT t FROM Book t")
})
public class ScienceJournal extends Book{
    private static final long serialVersionUID = 1L;

    @Column(name = "scienceIndex")
    private Integer scienceIndex;

    /* Science index */
    public Integer getScienceIndex() {
        return scienceIndex;
    }

    public void setScienceIndex(Integer scienceIndex) {
        this.scienceIndex = scienceIndex;
    }

    /* Science journal's total price */
    @Override
    public Double calcTotalPrice() {
        return  getQuantity() * getUnitPrice() * scienceIndex;
    }

    /* Update Science journal's attributes */
    @Override
    public Boolean updateItem(String key, String value) {
        switch(key) {
            case "scienceIndex": //special for science journal
                setScienceIndex(Integer.parseInt(value));
                break;
            default:
                return super.updateItem(key, value); // Update main attributes;
        }
        return true;
    }

}
