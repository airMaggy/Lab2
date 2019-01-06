package ru.mr.GLab2.server.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ComboModel implements Serializable {
    private static final String DIRECTORY = "Lab2_BD.out";
    private List<Author> authors;
    private List<Book> books;

    public ComboModel() throws IOException, ClassNotFoundException {
        final File file = new File(DIRECTORY);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fis);
            ComboModel CM = (ComboModel) oin.readObject();
            setAuthors(CM.getAuthors());
            setBooks(CM.getBooks());
        } else {
            this.authors = new ArrayList<>();
            this.books = new ArrayList<>();
        }
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setSerilCM() throws IOException {
        final File file = new File(DIRECTORY);
        if (file.exists()) {
            file.delete();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    //А теперь немного я покожу, хе-хе
    public long getCountAuthors() {
        return authors.size();
    }

    public long getCountBooks() {
        return books.size();
    }

    //Хватит пожалуй)
    public Book getBookByName(String name) {
        return this.books.stream().filter(x -> x.getNameBook().equals(name)).findFirst().orElse(null);
    }

    public Author getAuthorByName(String name) {
        return this.authors.stream().filter(x -> x.getNameAuthor().equals(name)).findFirst().orElse(null);
    }

    public Book getBookById(Long id) {
        return this.books.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    public Author getAuthorById(Long id) {
        return this.authors.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    public Long addBook(Book book) {
        books.add(book);
        return book.getId();
    }

    public Long addAuthor(Author author) {

        authors.add(author);
        return author.getId();
    }

    public void delBook(Book book) {
        books.remove(book);
    }

    //дописал методы удаления по айдишнику, потому что могу
    public void delBook(long id) {
        books.remove(books.stream().filter((x) -> x.getId().equals(id)).findFirst().orElse(null));
    }

    public void delAuthor(Author author) {
        authors.remove(author);
    }

    public void delAuthor(long id) {
        authors.remove(authors.stream().filter((x) -> x.getId().equals(id)).findFirst().orElse(null));
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