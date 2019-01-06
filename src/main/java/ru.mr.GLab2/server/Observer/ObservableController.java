package ru.mr.GLab2.server.Observer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ObservableController implements Observable {
    private List<Observer> observers;
    public ObservableController()
    {
        observers = new LinkedList<>();
    }

    @Override
    public void registerObserver(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void removeObserver(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObserversAboutCreateAuthor(Long id) throws IOException {
        for(Observer observer : observers)
            observer.createAuthorData(id);
    }

    @Override
    public void notifyObserversAboutCreateBook(Long id) throws IOException {
        for(Observer observer : observers)
            observer.createBookData(id);
    }

    @Override
    public void notifyObserversAboutChangeAuthor(Long id) throws IOException {
        for(Observer observer : observers)
            observer.changeAuthorData(id);
    }

    @Override
    public void notifyObserversAboutChangeBook(Long id) throws IOException {
        for(Observer observer : observers)
            observer.changeBookData(id);
    }

    @Override
    public void notifyObserversAboutDeleteAuthor(Long id) throws IOException {
        for(Observer observer : observers)
            observer.deleteAuthorData(id);
    }

    @Override
    public void notifyObserversAboutDeleteBook(Long id) throws IOException {
        for(Observer observer : observers)
            observer.deleteBookData(id);
    }
}
