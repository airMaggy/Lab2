package ru.mr.GLab2.util.dao;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.AuthorsForBook;
import ru.mr.GLab2.server.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private Connection connection;

    public BookDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        try {
            Book book;
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM %s", Book.TABLE_NAME));
            ResultSet books = preparedStatement.executeQuery();
            while (books.next()) {
                book = new Book();
                book.setId(books.getLong(Book.ID_COLUMN));
                book.setNameBook(books.getString(Book.NAME_BOOK_COLUMN));
                book.setHappyYear(books.getLong(Book.HAPPY_YEAR_COLUMN));
                book.setGenre(books.getString(Book.GENRE_COLUMN));
                book.setPages(books.getLong(Book.PAGES_COLUMN));
                book.setAuthors(findAuthorsForBookById(book.getId()));
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Book findById(Long id) {
        Book book = new Book();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM %s WHERE %s = ?", Book.TABLE_NAME, Book.ID_COLUMN));
            preparedStatement.setLong(1, id);
            ResultSet books = preparedStatement.executeQuery();
            while (books.next()) {
                book.setId(books.getLong(Book.ID_COLUMN));
                book.setNameBook(books.getString(Book.NAME_BOOK_COLUMN));
                book.setHappyYear(books.getLong(Book.HAPPY_YEAR_COLUMN));
                book.setGenre(books.getString(Book.GENRE_COLUMN));
                book.setPages(books.getLong(Book.PAGES_COLUMN));
                book.setAuthors(findAuthorsForBookById(book.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Long insert(Book book) {
        Long id = 0L;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                    Book.TABLE_NAME, Book.NAME_BOOK_COLUMN, Book.HAPPY_YEAR_COLUMN, Book.GENRE_COLUMN, Book.PAGES_COLUMN),
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getNameBook());
            preparedStatement.setLong(2, book.getHappyYear());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setLong(4, book.getPages());
            preparedStatement.execute();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                id = generatedKey.getLong(1);

                for (Author author : book.getAuthors()) {
                    PreparedStatement authorsForBookStatement = connection.prepareStatement(String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                            AuthorsForBook.TABLE_NAME, AuthorsForBook.AUTHOR_ID_COLUMN, AuthorsForBook.BOOK_ID_COLUMN));
                    authorsForBookStatement.setLong(1, author.getId());
                    authorsForBookStatement.setLong(2, id);
                    authorsForBookStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public void update(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                    Book.TABLE_NAME, Book.NAME_BOOK_COLUMN, Book.HAPPY_YEAR_COLUMN, Book.GENRE_COLUMN, Book.PAGES_COLUMN, Book.ID_COLUMN));
            preparedStatement.setString(1, book.getNameBook());
            preparedStatement.setLong(2, book.getHappyYear());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setLong(4, book.getPages());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Book book) {
        try {
            PreparedStatement authorsForBookStatement = connection.prepareStatement(String.format("DELETE FROM %s WHERE %s = ?", AuthorsForBook.TABLE_NAME, AuthorsForBook.BOOK_ID_COLUMN));
            authorsForBookStatement.setLong(1, book.getId());
            authorsForBookStatement.executeUpdate();
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("DELETE FROM %s WHERE %s = ?", Book.TABLE_NAME, Book.ID_COLUMN));
            preparedStatement.setLong(1, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long count() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT COUNT(*) FROM %s",
                    Book.TABLE_NAME, Book.ID_COLUMN));
            ResultSet count = preparedStatement.executeQuery();
            if (count.next()) {
                return count.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public List<Author> findAuthorsForBookById(Long id) {
        List<Author> result = new ArrayList<>();
        try {
            Author author;
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM %s WHERE %s IN (SELECT %s FROM %s WHERE %s = ?)",
                    Author.TABLE_NAME, Author.ID_COLUMN, AuthorsForBook.AUTHOR_ID_COLUMN, AuthorsForBook.TABLE_NAME, AuthorsForBook.BOOK_ID_COLUMN));
            preparedStatement.setLong(1, id);
            ResultSet authors = preparedStatement.executeQuery();
            while (authors.next()) {
                author = new Author();
                author.setId(authors.getLong(Author.ID_COLUMN));
                author.setNameAuthor(authors.getString(Author.NAME_AUTHOR_COLUMN));
                author.setHappyYear(authors.getLong(Author.HAPPY_YEAR_COLUMN));
                author.setSadYear(authors.getLong(Author.SAD_YEAR_COLUMN));
                author.setHappyPlace(authors.getString(Author.HAPPY_PLACE_COLUMN));
                author.setIntFacts(authors.getString(Author.INT_FACTS_COLUMN));
                author.setAlive(authors.getBoolean(Author.ALIVE_COLUMN));
                result.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
