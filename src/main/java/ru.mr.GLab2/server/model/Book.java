package ru.mr.GLab2.server.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "book")
@XmlType(name = "book", propOrder = {"nameBook", "happyYear", "genre", "pages", "authors"})
public class Book implements Serializable {
    private Long id;
    private String nameBook;
    private List<Author> authors;
    private Long happyYear;
    private String genre;
    private Long pages;
    //private boolean busy;


    //_____________________СДЕЛАТЬ НОРМ ГЕНЕРАЦИЮ ID
    public Book() {
        this(0L, "", 0L, "");
    }

    public Book(Long id, String nameBook, Long happyYear, String genre) {
        this.id = id;
        this.authors = new ArrayList<>();
        this.nameBook = nameBook;
        this.happyYear = happyYear;
        this.genre = genre;
        //this.busy = false;
    }

    public Long getId() {
        return id;
    }

    @XmlAttribute(required = true)
    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBook() {
        return nameBook;
    }

    @XmlElement(required = true, name = "name")
    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public Long getHappyYear() {
        return happyYear;
    }

    @XmlElement(required = true)
    public void setHappyYear(Long happyYear) {
        this.happyYear = happyYear;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @XmlElement(required = true, name = "author")
    @XmlElementWrapper(required = true)
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getGenre() {
        return genre;
    }

    @XmlElement(required = true)
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getPages() {
        return pages;
    }

    @XmlElement(required = true)
    public void setPages(Long pages) {
        this.pages = pages;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof Book && this.id.equals(((Book) obj).getId());
    }

    public String toString() {
        return id + ", " + nameBook + ", " + happyYear;
    }

 /*   public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public String getBusy() {return busy?"y":"n";}*/
}
