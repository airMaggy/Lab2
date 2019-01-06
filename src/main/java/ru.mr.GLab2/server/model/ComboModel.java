package ru.mr.GLab2.server.model;

import ru.mr.GLab2.util.Xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "model")
@XmlType(name = "model", propOrder = {"authors", "books"})
public class ComboModel implements Serializable {
    private static final String DIRECTORY = "BD.xml";
    private List<Author> authors;
    private List<Book> books;

    public ComboModel() {
        this.authors = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public void loadData() {
        final File file = new File(DIRECTORY);
        if (file.exists()) {
            ComboModel CM = Xml.loadModelFromFile(file);
            setAuthors(CM.getAuthors());
            setBooks(CM.getBooks());
            for (Book book : CM.getBooks()) {
                for (Author author : book.getAuthors()) {
                    CM.getAuthorById(author.getId()).getBooks().add(CM.getBookById(book.getId()));
                }
            }
        }
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @XmlElement(name = "author")
    @XmlElementWrapper(required = true, nillable = true)
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Book> getBooks() {
        return books;
    }

    @XmlElement(name = "book")
    @XmlElementWrapper(required = true, nillable = true)
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setSerilCM() {
        final File file = new File(DIRECTORY);
        if (file.exists()) {
            file.delete();
        }
        Xml.saveModelToFile(this, file);
    }

    //А теперь немного я покожу, хе-хе
    public long getCountAuthors() {
        return authors.size();
    }

    public long getCountBooks() {
        return books.size();
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