package ru.mr.GLab2.util.dao;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book findById(Long id);
    Long insert(Book book);
    void update(Book book);
    void delete(Book book);
    Long count();
    List<Author> findAuthorsForBookById(Long id);
}
