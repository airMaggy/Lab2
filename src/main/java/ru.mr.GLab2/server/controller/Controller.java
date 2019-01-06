package ru.mr.GLab2.server.controller;

import ru.mr.GLab2.server.Observer.ObservableController;
import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;
import ru.mr.GLab2.server.model.ComboModel;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Controller {
    private ComboModel CM;
    private ObservableController observableController;

    public Controller(ComboModel CM) {
        this.CM = CM;
        observableController = new ObservableController();
    }

    //observer

    public ObservableController getObservableController() {
        return observableController;
    }
    //removerы
    public boolean removeBookInModel(Long id) throws IOException {
        Book book = CM.getBookById(id);
        if (book != null) {
            book.getAuthors().forEach(a -> a.getBooks().remove(book));
            CM.delBook(book);
            CM.setSerilCM();
            observableController.notifyObserversAboutDeleteBook(id);
            return true;
        }
        return false;
    }

    public boolean removeAuthorInModel(Long id) throws IOException {
        Author author = CM.getAuthorById(id);
        if (author != null && author.getBooks().isEmpty()) {
            CM.delAuthor(author);
            CM.setSerilCM();
            observableController.notifyObserversAboutDeleteAuthor(id);
            return true;
        }
        return false;
    }


    //СДЕЛАТЬ THROW ILLEGAL ARGUMENT
    public long createAuthor(Author author) throws IOException {

        if (checkDateAuthor(author.getHappyYear(), author.getSadYear()) || (author.isAlive() && checkDateAliveAuthor(author.getHappyYear()))) {
            Long ID = 0L;
            while (CM.getAuthorById(ID) != null) {
                ID++;
            }
            author.setId(ID);
            CM.addAuthor(author);
            CM.setSerilCM();
            observableController.notifyObserversAboutCreateAuthor(author.getId());
            return author.getId();
        } else {
            return -1L;
        }
    }
    public long createBook(Book book) throws IOException {
        if (book != null && checkDateBook(book.getAuthors(), book.getHappyYear()) && book.getPages() > 0) {
            Long ID = 0L;
            while (CM.getBookById(ID) != null) {
                ID++;
            }
            book.setId(ID);
            book.getAuthors().forEach(a -> CM.getAuthorById(a.getId()).getBooks().add(book));
            CM.addBook(book);
            CM.setSerilCM();
            observableController.notifyObserversAboutCreateBook(book.getId());
            return book.getId();
        }
        //насколько же проще передавать обьекты))
        return -1L;
    }
    //changerыхреновыматьих
    public long changeAuthor(Author tmpA) throws IOException {
        if (tmpA != null && checkDateAuthorAndBooks(tmpA.getId(), tmpA.getHappyYear()) && (checkDateAuthor(tmpA.getHappyYear(), tmpA.getSadYear()) || (tmpA.isAlive() && checkDateAliveAuthor(tmpA.getHappyYear())))) {
            List<Book> books = CM.getAuthorById(tmpA.getId()).getBooks();
            CM.getAuthorById(tmpA.getId()).getBooks().forEach(b -> b.getAuthors().remove(tmpA));
            CM.getAuthorById(tmpA.getId()).getBooks().clear();
            removeAuthorInModel(tmpA.getId());
            long id = createAuthor(tmpA);
            CM.getAuthorById(id).setBooks(books);
            CM.getAuthorById(id).getBooks().forEach(b -> b.getAuthors().add(CM.getAuthorById(id)));
            CM.setSerilCM();
            observableController.notifyObserversAboutChangeAuthor(id);
            return id;
        } else {
            return -1L;
        }
    }
    public long changeBook(Book book) throws IOException {

        if (book != null && checkDateBook(book.getAuthors(), book.getHappyYear())) {
            CM.getBookById(book.getId()).getAuthors().forEach(a -> CM.getAuthorById(a.getId()).getBooks().remove(book));
            removeBookInModel(book.getId());

            long id = createBook(book);
            book.getAuthors().forEach(a -> CM.getAuthorById(a.getId()).getBooks().add(book));
            CM.setSerilCM();
            observableController.notifyObserversAboutChangeBook(id);
            return id;
        } else {
            return -1L;
        }
    }
    private boolean checkDateAuthor(Long dateHappy, Long dateSad) { //проверка на годы жизни
        return (dateSad - dateHappy > 10) && (dateSad - dateHappy < 150) && (dateHappy > 0) && (dateHappy < 2019) && (dateSad > 0) && (dateSad < 2019);
    }

    private boolean checkDateAliveAuthor(Long dateHappy) {//проверка живого автора
        return 2019 - dateHappy < 150 && dateHappy > 0;
    }

    private boolean checkDateBook(Set<Author> tmpAuth, Long dateBook) {//проверка на авторов книжки
        return tmpAuth.stream().allMatch((x) -> x.getHappyYear() + 10 < dateBook);
    }

    private boolean checkDateAuthorAndBooks(Long id, Long dateHappy) {
        return CM.getAuthorById(id).getBooks().stream().allMatch((a) -> a.getHappyYear() > dateHappy + 10);
    }
}
