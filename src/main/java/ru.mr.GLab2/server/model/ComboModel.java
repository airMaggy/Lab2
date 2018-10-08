package ru.mr.GLab2.server.model;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ComboModel implements Serializable {
    private static final String DIRECTORY = "D:\\Гаврилов\\Lab2_BD.out";
    private Set<Author> authors;
    private Set<Book> books;

    public ComboModel() throws IOException, ClassNotFoundException {
        final File file = new File(DIRECTORY);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fis);
            ComboModel CM = (ComboModel) oin.readObject();
            setAuthors(CM.getAuthors());
            setBooks(CM.getBooks());
        } else {
            this.authors = new HashSet<>();
            this.books = new HashSet<>();
        }
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void setSerilCM() throws IOException {
        final File file = new File(DIRECTORY);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public Book getBookByName(String name) {
        return this.books.stream().filter((x) -> x.getNameBook().equals(name)).findFirst().orElse(null);
    }

    public Author getAuthorByName(String name) {
        return this.authors.stream().filter((x) -> x.getNameAuthor().equals(name)).findFirst().orElse(null);
    }

    public Book getBookById(Long id) {
        return this.books.stream().filter((x) -> x.getId().equals(id)).findFirst().orElse(null);
    }

    public Author getAuthorById(Long id) {
        return this.authors.stream().filter((x) -> x.getId().equals(id)).findFirst().orElse(null);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void delBook(Book book) {
        books.remove(book);
    }

    public void delAuthor(Author author) {
        authors.remove(author);
    }

    public void setBook(Book book) {
        delBook(this.books.stream().filter((x) -> x.getId().equals(book.getId())).findFirst().orElse(null));
        addBook(book);
    }

    public void setAuthor(Author author) {
        delAuthor(this.authors.stream().filter((x) -> x.getNameAuthor().equals(author.getNameAuthor())).findFirst().orElse(null));
        addAuthor(author);
    }
}