package ru.mr.GLab2.client.view.version2;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;
import ru.mr.GLab2.util.Net;

public class ClientWriter {
    private Net net;

    public ClientWriter(Net net) {
        this.net = net;
    }

    synchronized public void basedGetAuthors() {
        net.sendCommand(Net.READ | Net.ALL | Net.AUTHOR | Net.BASED);
    }

    synchronized public void basedGetBooks() {
        net.sendCommand(Net.READ | Net.ALL | Net.BOOK | Net.BASED);
    }

    synchronized public void newAuthorGetAuthor(Long id) {
        net.sendCommand(Net.READ | Net.AUTHOR | Net.NEW);
        net.sendObject(id);
    }

    synchronized public void newAuthorUpdateAuthor(Author author) {
        net.sendCommand(Net.UPDATE | Net.AUTHOR | Net.NEW);
        net.sendObject(author);
    }

    synchronized public void newAuthorCreateAuthor(Author author) {
        net.sendCommand(Net.CREATE | Net.AUTHOR | Net.NEW);
        net.sendObject(author);
    }

    synchronized public void oneOfAuthorGetAuthor(Long id) {
        net.sendCommand(Net.READ | Net.AUTHOR | Net.ONE);
        net.sendObject(id);
    }

    synchronized public void oneOfAuthorDeleteAuthorById(Long id) {
        net.sendCommand(Net.DELETE | Net.AUTHOR | Net.ONE);
        net.sendObject(id);
    }

    synchronized public void newBookGetBook(Long id) {
        net.sendCommand(Net.READ | Net.BOOK | Net.NEW);
        net.sendObject(id);
    }

    synchronized public void newBookCreateBook(Book book) {
        net.sendCommand(Net.CREATE | Net.BOOK | Net.NEW);
        net.sendObject(book);
    }

    synchronized public void newBookUpdateBook(Book book) {
        net.sendCommand(Net.UPDATE | Net.BOOK | Net.NEW);
        net.sendObject(book);
    }

    synchronized public void oneOfBookGetBook(Long id) {
        net.sendCommand(Net.READ | Net.BOOK | Net.ONE);
        net.sendObject(id);
    }

    synchronized public void oneOfBookDeleteBookById(Long id) {
        net.sendCommand(Net.DELETE | Net.BOOK | Net.ONE);
        net.sendObject(id);
    }

    synchronized public void observerBasedChangeAuthor(Long id) {
        net.sendCommand(Net.NOTIFY | Net.UPDATE | Net.AUTHOR);
        net.sendObject(id);
    }

    synchronized public void observerBasedChangeBook(Long id) {
        net.sendCommand(Net.NOTIFY | Net.UPDATE | Net.BOOK);
        net.sendObject(id);
    }

    synchronized public void exitClient() {
        net.sendCommand(Net.EXIT);
    }

    synchronized public void observerBasedCreateAuthor(Long id) {
        net.sendCommand(Net.NOTIFY | Net.CREATE | Net.AUTHOR);
        net.sendObject(id);
    }

    synchronized public void observerBasedCreateBook(Long id) {
        net.sendCommand(Net.NOTIFY | Net.CREATE | Net.BOOK);
        net.sendObject(id);
    }
}
