package ru.mr.GLab2.util.dao;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.AuthorsForBook;
import ru.mr.GLab2.server.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private Connection connection;

    public AuthorDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Author> findAll() {
        List<Author> result = new ArrayList<>();
        try {
            Author author;
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM %s", Author.TABLE_NAME));
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
                author.setBooks(findBooksForAuthorById(author.getId()));
                result.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Author findById(Long id) {
        Author author = new Author();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM %s WHERE %s = ?", Author.TABLE_NAME, Author.ID_COLUMN));
            preparedStatement.setLong(1, id);
            ResultSet authors = preparedStatement.executeQuery();
            while (authors.next()) {
                author.setId(authors.getLong(Author.ID_COLUMN));
                author.setNameAuthor(authors.getString(Author.NAME_AUTHOR_COLUMN));
                author.setHappyYear(authors.getLong(Author.HAPPY_YEAR_COLUMN));
                author.setSadYear(authors.getLong(Author.SAD_YEAR_COLUMN));
                author.setHappyPlace(authors.getString(Author.HAPPY_PLACE_COLUMN));
                author.setIntFacts(authors.getString(Author.INT_FACTS_COLUMN));
                author.setAlive(authors.getBoolean(Author.ALIVE_COLUMN));
                author.setBooks(findBooksForAuthorById(author.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    public Long insert(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                    Author.TABLE_NAME, Author.NAME_AUTHOR_COLUMN, Author.HAPPY_YEAR_COLUMN, Author.SAD_YEAR_COLUMN, Author.HAPPY_PLACE_COLUMN, Author.INT_FACTS_COLUMN, Author.ALIVE_COLUMN),
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, author.getNameAuthor());
            preparedStatement.setLong(2, author.getHappyYear());
            preparedStatement.setLong(3, author.getSadYear());
            preparedStatement.setString(4, author.getHappyPlace());
            preparedStatement.setString(5, author.getIntFacts());
            preparedStatement.setBoolean(6, author.isAlive());
            preparedStatement.execute();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void update(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                    Author.TABLE_NAME, Author.NAME_AUTHOR_COLUMN, Author.HAPPY_YEAR_COLUMN, Author.SAD_YEAR_COLUMN, Author.HAPPY_PLACE_COLUMN, Author.INT_FACTS_COLUMN, Author.ALIVE_COLUMN, Author.ID_COLUMN));
            preparedStatement.setString(1, author.getNameAuthor());
            preparedStatement.setLong(2, author.getHappyYear());
            preparedStatement.setLong(3, author.getSadYear());
            preparedStatement.setString(4, author.getHappyPlace());
            preparedStatement.setString(5, author.getIntFacts());
            preparedStatement.setBoolean(6, author.isAlive());
            preparedStatement.setLong(7, author.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("DELETE FROM %s WHERE %s = ?", Author.TABLE_NAME, Author.ID_COLUMN));
            preparedStatement.setLong(1, author.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long count() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT COUNT(*) FROM %s",
                    Author.TABLE_NAME, Author.ID_COLUMN));
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
    public List<Book> findBooksForAuthorById(Long id) {
        List<Book> result = new ArrayList<>();
        try {
            Book book;
            PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM %s WHERE %s IN (SELECT %s FROM %s WHERE %s = ?)",
                    Book.TABLE_NAME, Book.ID_COLUMN, AuthorsForBook.BOOK_ID_COLUMN, AuthorsForBook.TABLE_NAME, AuthorsForBook.AUTHOR_ID_COLUMN));
            preparedStatement.setLong(1, id);
            ResultSet books = preparedStatement.executeQuery();
            while (books.next()) {
                book = new Book();
                book.setId(books.getLong(Book.ID_COLUMN));
                book.setNameBook(books.getString(Book.NAME_BOOK_COLUMN));
                book.setHappyYear(books.getLong(Book.HAPPY_YEAR_COLUMN));
                book.setGenre(books.getString(Book.GENRE_COLUMN));
                book.setPages(books.getLong(Book.PAGES_COLUMN));
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
