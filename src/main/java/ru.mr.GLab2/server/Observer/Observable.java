package ru.mr.GLab2.server.Observer;

import java.io.IOException;

public interface Observable {
    void registerObserver(Observer obj);
    void removeObserver(Observer obj);
    void notifyObserversAboutCreateAuthor(Long id) throws IOException;
    void notifyObserversAboutCreateBook(Long id) throws IOException;
    void notifyObserversAboutChangeAuthor(Long id) throws IOException;
    void notifyObserversAboutChangeBook(Long id) throws IOException;
    void notifyObserversAboutDeleteAuthor(Long id) throws IOException;
    void notifyObserversAboutDeleteBook(Long id) throws IOException;
}
