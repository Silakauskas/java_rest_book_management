package com.SS.restapi.models;

import javax.persistence.*;
import com.SS.restapi.models.Constants;


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
        if (key.equals("scienceIndex")){
            setScienceIndex(Integer.parseInt(value));    // Special for science journal
            return true;
        } else {
            return super.updateItem(key, value);        // Update main attributes
        }
    }

}
