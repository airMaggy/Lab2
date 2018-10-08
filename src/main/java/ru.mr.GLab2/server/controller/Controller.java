package ru.mr.GLab2.server.controller;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;
import ru.mr.GLab2.server.model.ComboModel;

import java.io.IOException;
import java.util.Set;

public class Controller {
    private ComboModel CM;

    public Controller(ComboModel CM) throws IOException, ClassNotFoundException {
        this.CM = CM;
    }

    //removerы
    public boolean removeBookInModel(Long id) throws IOException {
        if (CM.getBookById(id) != null) {
            CM.delBook(CM.getBookById(id));
            CM.setSerilCM();
            return true;
        } else return false;
    }

    public boolean removeAuthorInModel(Long id) throws IOException {
        if (CM.getAuthorById(id) != null && CM.getAuthorById(id).getBooks().isEmpty()) {
            CM.delAuthor(CM.getAuthorById(id));
            CM.setSerilCM();
            return true;
        } else return false;
    }


    //СДЕЛАТЬ THROW ILLEGAL ARGUMENT
    public boolean createAuthor(String nameAuthor, Long happyYear, Long sadYear, String happyPlace, String intFacts, boolean alive) throws IOException {
        if (checkDateAuthor(happyYear, sadYear) || (alive && checkDateAliveAuthor(happyYear))) {


            Author author = new Author();
            while (CM.getAuthorById(author.getId()) != null) {
                Author.nextID();
                author.setId(Author.ID);
            }

            if (alive) author.setSadYear(0L);
            else author.setSadYear(sadYear);

            author.setNameAuthor(nameAuthor);
            author.setHappyYear(happyYear);
            author.setHappyPlace(happyPlace);
            author.setIntFacts(intFacts);
            author.setAlive(alive);
            CM.addAuthor(author);
            CM.setSerilCM();
            return true;
        } else return false;
    }

    public boolean createBook(String nameBook, Long happyYear, Set<Author> authors, String genre, Long pages) throws IOException {
        Book book = new Book();
        if (checkDateBook(authors, happyYear) && pages > 0) {
            while (CM.getBookById(book.getId()) != null) {
                Book.nextID();
                book.setId(Book.ID);
            }
            book.setNameBook(nameBook);
            book.setHappyYear(happyYear);
            book.setAuthors(authors);
            book.setGenre(genre);
            book.setPages(pages);
            CM.addBook(book);
            authors.stream().forEach((a) -> a.getBooks().add(book));
            CM.setSerilCM();
            return true;
        } else return false;
    }
    //changerы

    public boolean changeAuthor(Long id, String newNameAuthor, Long newHappyYear, Long newSadYear, String newHappyPlace, String newIntFacts, boolean alive) throws IOException {
        Author tmpA = CM.getAuthorById(id);
        //ПРОВЕРКА НА ГОД КНИГ МАААААААААААААААААААААААААААУ
        //тут про alive
        if (tmpA != null && checkDateAuthorAndBooks(id, newHappyYear) && (checkDateAuthor(newHappyYear, newSadYear) || (alive && checkDateAliveAuthor(newHappyYear)))) {
            if (alive) tmpA.setSadYear(0L);
            else tmpA.setSadYear(newSadYear);

            tmpA.setHappyYear(newHappyYear);
            tmpA.setNameAuthor(newNameAuthor);
            tmpA.setHappyPlace(newHappyPlace);
            tmpA.setIntFacts(newIntFacts);
            tmpA.setAlive(alive);
            CM.setSerilCM();
            return true;
        } else return false;
    }

    public boolean changeBook(Long id, String newNameBook, Set<Author> newCollectAuthor, Long newHappyYear, String newMainGenre, Long pages) throws IOException {
        Book tmpB = CM.getBookById(id);
        if (tmpB != null && checkDateBook(newCollectAuthor, newHappyYear)) {
            tmpB.setNameBook(newNameBook);
            tmpB.setHappyYear(newHappyYear);
            tmpB.setGenre(newMainGenre);
            tmpB.setPages(pages);

            //mb mozhno prozshe?
            tmpB.getAuthors().stream().forEach((a) -> a.getBooks().remove(tmpB));
            tmpB.setAuthors(newCollectAuthor);
            tmpB.getAuthors().stream().forEach((a) -> a.getBooks().add(tmpB));

            CM.setSerilCM();
            return true;
        } else return false;
    }

    private boolean checkDateAuthor(Long dateHappy, Long dateSad) { //проверка на годы жизни
        return (dateSad - dateHappy > 10) && (dateSad - dateHappy < 150) && (dateHappy > 0) && (dateHappy < 2019) && (dateSad > 0) && (dateSad < 2019);
    }

    private boolean checkDateAliveAuthor(Long dateHappy) {//проверка живого автора
        return 2018 - dateHappy < 150 && dateHappy > 0;
    }

    private boolean checkDateBook(Set<Author> tmpAuth, Long dateBook) {//проверка на авторов книжки
        return tmpAuth.stream().allMatch((x) -> x.getHappyYear() + 10 < dateBook);
    }

    private boolean checkDateAuthorAndBooks(Long id, Long dateHappy) {
        return CM.getAuthorById(id).getBooks().stream().allMatch((a) -> a.getHappyYear() > dateHappy + 10);
    }
}
