package ru.mr.GLab2.util.dao;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;

import java.util.List;

public interface AuthorDao {
    List<Author> findAll();
    Author findById(Long id);
    Long insert(Author author);
    void update(Author author);
    void delete(Author author);
    Long count();
    List<Book> findBooksForAuthorById(Long id);
}
