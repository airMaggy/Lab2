package ru.mr.GLab2.server.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "author")
@XmlType(name = "author", propOrder = {"nameAuthor", "alive", "happyYear", "sadYear", "happyPlace", "intFacts"})
public class Author implements Serializable {
    private Long id;
    private String nameAuthor;
    private Long happyYear;
    private Long sadYear;
    private String happyPlace;
    private String intFacts;
    private boolean alive;
    //private boolean busy;
    private List<Book> books;

    public Author() {
        this(0L, "", 0L, 0L, "", "", false);
    }


    //____________________СДЕЛАТЬ НОРМ ГЕНЕРАЦИЮ ID
    public Author(Long id, String nameAuthor, Long happyYear, Long sadYear, String happyPlace, String interestingFacts, boolean alive) {
        this.id = id;
        this.nameAuthor = nameAuthor;
        this.happyYear = happyYear;
        this.sadYear = sadYear;
        this.happyPlace = happyPlace;
        this.intFacts = interestingFacts;
        this.alive = alive;
        //this.busy = false;
        this.books = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    @XmlAttribute(required = true)
    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    @XmlElement(required = true, name = "name")
    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public Long getHappyYear() {
        return happyYear;
    }

    @XmlElement(required = true)
    public void setHappyYear(Long happyYear) {
        this.happyYear = happyYear;
    }

    public Long getSadYear() {
        return sadYear;
    }

    @XmlElement(required = true)
    public void setSadYear(Long sadYear) {
        this.sadYear = sadYear;
    }

    public String getHappyPlace() {
        return happyPlace;
    }

    @XmlElement(required = true)
    public void setHappyPlace(String happyPlace) {
        this.happyPlace = happyPlace;
    }

    public String getIntFacts() {
        return intFacts;
    }

    @XmlElement(required = true)
    public void setIntFacts(String intFacts) {
        this.intFacts = intFacts;
    }

    public boolean isAlive() {
        return alive;
    }

    @XmlElement(required = true)
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public List<Book> getBooks() {
        return books;
    }

    @XmlTransient
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int hashCode() {
        return super.hashCode();
    }


    public boolean equals(Object obj) {
        return obj instanceof Author && this.id.equals(((Author) obj).getId());
    }


    public String toString() {
        return id + ", " + nameAuthor + ", " + happyYear;
    }

 /*   public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    public String getBusy() {return busy?"y":"n";}*/
}
