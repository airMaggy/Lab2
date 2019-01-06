package ru.mr.GLab2.server.model;

import ru.mr.GLab2.util.dao.AuthorDao;
import ru.mr.GLab2.util.dao.AuthorDaoImpl;
import ru.mr.GLab2.util.dao.BookDao;
import ru.mr.GLab2.util.dao.BookDaoImpl;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;


public class ComboModel implements Serializable {
    private AuthorDao authorDao;
    private BookDao bookDao;

    public ComboModel(Connection connection) {
        authorDao = new AuthorDaoImpl(connection);
        bookDao = new BookDaoImpl(connection);
    }

    public List<Author> getAuthors() {
        return authorDao.findAll();
    }

    public void setAuthors(List<Author> authors) {
        authorDao.findAll().forEach(author -> authorDao.delete(author));
        authors.forEach(author -> authorDao.insert(author));
    }

    public List<Book> getBooks() {
        return bookDao.findAll();
    }

    public void setBooks(List<Book> books) {
        bookDao.findAll().forEach(book -> bookDao.delete(book));
        books.forEach(book -> bookDao.insert(book));
    }

    public long getCountAuthors() {
        return authorDao.count();
    }

    public long getCountBooks() {
        return bookDao.count();
    }

    public Book getBookById(Long id) {
        return bookDao.findById(id);
    }

    public Author getAuthorById(Long id) {
        return authorDao.findById(id);
    }

    public Long addBook(Book book) {
        return bookDao.insert(book);
    }

    public Long addAuthor(Author author) {
        return authorDao.insert(author);
    }

    public void delBook(Book book) {
        bookDao.delete(book);
    }

    public void delAuthor(Author author) {
        authorDao.delete(author);
    }

    public void setBook(Book book) {
        if (bookDao.findById(book.getId()).equals(new Book())) {
            bookDao.insert(book);
        } else {
            bookDao.update(book);
        }
    }

    public void setAuthor(Author author) {
        if (authorDao.findById(author.getId()).equals(new Author())) {
            authorDao.insert(author);
        } else {
            authorDao.update(author);
        }
    }
}