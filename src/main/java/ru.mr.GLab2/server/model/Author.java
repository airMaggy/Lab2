package ru.mr.GLab2.server.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Author implements Serializable {
    public static long ID = 0;

    private Long id;
    private String nameAuthor;
    private Long happyYear;
    private Long sadYear;
    private String happyPlace;
    private String intFacts;
    private boolean alive;
    private Set<Book> books;

    public Author() {
        this(0L, "", 0L, 0L, "", "", false);
    }


    //____________________СДЕЛАТЬ НОРМ ГЕНЕРАЦИЮ ID
    public Author(Long id, String nameAuthor, Long happyYear, Long sadYear, String happyPlace, String interestingFacts, boolean alive) {
        this.id = ID;
        ID++;
        // /this.id = id;
        this.nameAuthor = nameAuthor;
        this.happyYear = happyYear;
        this.sadYear = sadYear;
        this.happyPlace = happyPlace;
        this.intFacts = interestingFacts;
        this.alive = alive;

        this.books = new HashSet<>();
    }

    public static void nextID() {
        ID++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public Long getHappyYear() {
        return happyYear;
    }

    public void setHappyYear(Long happyYear) {
        this.happyYear = happyYear;
    }

    public Long getSadYear() {
        return sadYear;
    }

    public void setSadYear(Long sadYear) {
        this.sadYear = sadYear;
    }

    public String getHappyPlace() {
        return happyPlace;
    }

    public void setHappyPlace(String happyPlace) {
        this.happyPlace = happyPlace;
    }

    public String getIntFacts() {
        return intFacts;
    }

    public void setIntFacts(String intFacts) {
        this.intFacts = intFacts;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
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
}
