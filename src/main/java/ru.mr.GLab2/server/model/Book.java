package ru.mr.GLab2.server.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Book implements Serializable {
    public static long ID = 0;

    private Long id;
    private String nameBook;
    private Set<Author> authors;
    private Long happyYear;
    private String genre;
    private Long pages;


    //_____________________СДЕЛАТЬ НОРМ ГЕНЕРАЦИЮ ID
    public Book() {
        this(0L, "", 0L, "");
    }

    public Book(Long id, String nameBook, Long happyYear, String genre) {
        this.id = ID;
        ID++;
        //this.id = id;
        this.authors = new HashSet<>();
        this.nameBook = nameBook;
        this.happyYear = happyYear;
        this.genre = genre;
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

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public Long getHappyYear() {
        return happyYear;
    }

    public void setHappyYear(Long happyYear) {
        this.happyYear = happyYear;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getPages() {
        return pages;
    }

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
}
