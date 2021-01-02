package com.SS.restapi.dao;

import com.SS.restapi.models.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BookDAO {

    @PersistenceContext(unitName = "restapi_PU")
    EntityManager manager;

    public List getAll() {
        return manager.createNamedQuery("Book.findAll", Book.class).getResultList();
    }

    public Book findByBarcode(Long barcode) {
        return manager.find(Book.class, barcode);
    }

    public void update(Book book) {
        manager.merge(book);
    }

    public void create(Book book) {
        manager.persist(book);
    }

    public void delete(Book book) {
        if (!manager.contains(book)) {
            book = manager.merge(book);
        }

        manager.remove(book);
    }
}