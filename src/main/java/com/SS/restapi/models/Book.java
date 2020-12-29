package com.SS.restapi.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "SELECT t FROM Book t")
})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "barcode")
    private Long barcode;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "unitPrice")
    private Double unitPrice;
    @Column(name = "type")
    private String type;

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
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }


    /* Type */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Book{" + "barcode=" + barcode +
                ", name=" + name +
                ", author=" + author +
                ", quantity=" + quantity +
                ", price per unit=" + unitPrice +
                '}';
    }

}