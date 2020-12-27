package com.SS.restapi.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "SELECT t FROM Book t")
})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long barcode;
    private String name;
    private String author;
    private Integer quantity;
    private Float unit_price;

    /* Barcode */
    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
        this.barcode = barcode;
    }


    /* Name */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /* Author */
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    /* Quantity */
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    /* Price per unit */
    public Float getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(Float unit_price) {
        this.unit_price = unit_price;
    }


    @Override
    public String toString() {
        return "Book{" + "barcode=" + barcode +
                ", name=" + name +
                ", author=" + author +
                ", quantity=" + quantity +
                ", price per unit=" + unit_price +
                '}';
    }

}