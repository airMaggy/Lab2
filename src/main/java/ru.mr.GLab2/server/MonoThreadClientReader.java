package ru.mr.GLab2.server;


import ru.mr.GLab2.server.Observer.Observer;
import ru.mr.GLab2.server.Observer.ObserversThread;
import ru.mr.GLab2.server.controller.Controller;
import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;
import ru.mr.GLab2.server.model.ComboModel;
import ru.mr.GLab2.util.Net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;


public class MonoThreadClientReader extends Thread {
    private ComboModel cm;
    private Controller contr;
    private Socket client;
    private Net net;
    private Observer observer;

    public MonoThreadClientReader(Socket socket, ComboModel cm, Controller contr) {
        this.cm = cm;
        this.contr = contr;
        this.client = socket;
    }

    @Override
    public void run() {
        try (OutputStream out = client.getOutputStream();
             InputStream in = client.getInputStream()) {
            net = new Net(in, out);
            observer = new ObserversThread(net, contr.getObservableController());
            System.out.println("out & in est'");
            System.out.println("poneslasya");
            boolean workPr = true;
            while (workPr) {
                int b = net.receiveCommand();
                System.out.println(net.decodeCommand(b));
                switch (b) {
                    case (Net.NOTIFY | Net.UPDATE | Net.AUTHOR): {
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            Author author = cm.getAuthorById(id);
                            net.sendCommand(Net.UPDATE | Net.AUTHOR | Net.BASED);
                            net.sendObject(author);
                        }
                        break;
                    }
                    case (Net.NOTIFY | Net.UPDATE | Net.BOOK): {
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            Book book = cm.getBookById(id);
                            net.sendCommand(Net.UPDATE | Net.BOOK | Net.BASED);
                            net.sendObject(book);
                        }
                        break;
                    }
                    case (Net.NOTIFY | Net.CREATE | Net.AUTHOR): {
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            Author author = cm.getAuthorById(id);
                            net.sendCommand(Net.CREATE | Net.AUTHOR | Net.BASED);
                            net.sendObject(author);
                        }
                        break;
                    }
                    case (Net.NOTIFY | Net.CREATE | Net.BOOK): {
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            Book book = cm.getBookById(id);
                            net.sendCommand(Net.CREATE | Net.BOOK | Net.BASED);
                            net.sendObject(book);
                        }
                        break;
                    }
                    case (Net.READ | Net.ALL | Net.AUTHOR | Net.BASED): {
                        synchronized (net) {
                            net.sendCommand(Net.READ | Net.ALL | Net.AUTHOR | Net.BASED);
                            net.sendObject(cm.getCountAuthors());
                            if (cm.getCountAuthors() > 0) {
                                cm.getAuthors().forEach(author -> net.sendObject(author));
                            }
                        }
                        break;
                    }
                    case (Net.READ | Net.ALL | Net.BOOK | Net.BASED): {//BASED запрос на все книги
                        synchronized (net) {
                            net.sendCommand(Net.READ | Net.ALL | Net.BOOK | Net.BASED);
                            net.sendObject(cm.getCountBooks());//записать количество передаваемых обьектов
                            if (cm.getCountBooks() > 0) {
                                cm.getBooks().forEach(book -> net.sendObject(book));
                            }
                        }
                        break;
                    }
                    case (Net.READ | Net.BOOK | Net.ONE): { //ONE OF BOOK GET BOOK
                        synchronized (net) {
                            net.sendCommand(Net.READ | Net.BOOK | Net.ONE);

                            Long id = (Long) net.receiveObject();
                            Book book = cm.getBookById(id);

                            net.sendObject(book);
                        }
                        break;

                    }
                    case (Net.CREATE | Net.BOOK | Net.NEW): {// NEWBOOK Добавить книгу в бд
                        synchronized (net) {
                            Book book = (Book) net.receiveObject();
                            Long id = contr.createBook(book);

                            net.sendCommand(Net.CREATE | Net.BOOK | Net.NEW);
                            net.sendObject(id);
                        }
                        break;
                    }
                    case (Net.UPDATE | Net.BOOK | Net.NEW): { // NEWBOOK изменить книгу в бд
                        synchronized (net) {
                            Book book = (Book) net.receiveObject();
                            Long id = contr.changeBook(book);

                            net.sendCommand(Net.UPDATE | Net.BOOK | Net.NEW);
                            net.sendObject(id);
                        }
                        break;
                    }
                    case (Net.DELETE | Net.BOOK | Net.ONE): { //ONEOFBOOK удаление книги
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            boolean ok = contr.removeBookInModel(id);

                            net.sendCommand(Net.DELETE | Net.BOOK | Net.ONE);
                            net.sendObject(id);
                            net.sendObject(ok);
                        }
                        break;
                    }
                    case (Net.READ | Net.BOOK | Net.NEW): { //NEW BOOK GET BOOK
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            Book book = cm.getBookById(id);

                            net.sendCommand(Net.READ | Net.BOOK | Net.NEW);
                            net.sendObject(book);
                        }
                        break;
                    }
                    case (Net.READ | Net.AUTHOR | Net.ONE): { //ONE OF AUTHOR GET AUTHOR
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            Author author = cm.getAuthorById(id);
                            net.sendCommand(Net.READ | Net.AUTHOR | Net.ONE);
                            net.sendObject(author);
                        }
                        break;
                    }
                    case (Net.CREATE | Net.AUTHOR | Net.NEW): { //NEW AUTHOR добавить автора в бд
                        synchronized (net) {
                            Author author = (Author) net.receiveObject();
                            Long id = contr.createAuthor(author);

                            net.sendCommand(Net.CREATE | Net.AUTHOR | Net.NEW);
                            net.sendObject(id);
                        }
                        break;
                    }
                    case (Net.UPDATE | Net.AUTHOR | Net.NEW): { // NEWAUTHOR изменить автора в бд
                        synchronized (net) {
                            Author author = (Author) net.receiveObject();
                            Long id = contr.changeAuthor(author);

                            net.sendCommand(Net.UPDATE | Net.AUTHOR | Net.NEW);
                            net.sendObject(id);
                        }
                        break;
                    }
                    case (Net.DELETE | Net.AUTHOR | Net.ONE): { //ONE OF AUTHOR удалить автора из бд
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            boolean ok = contr.removeAuthorInModel(id);

                            net.sendCommand(Net.DELETE | Net.AUTHOR | Net.ONE);
                            net.sendObject(id);
                            net.sendObject(ok);
                        }
                        break;
                    }
                    case (Net.READ | Net.AUTHOR | Net.NEW): { //NEW AUTHOR получить книгу, которую мы жаждем изменить
                        synchronized (net) {
                            Long id = (Long) net.receiveObject();
                            Author author = cm.getAuthorById(id);

                            net.sendCommand(Net.READ | Net.AUTHOR | Net.NEW);
                            net.sendObject(author);
                        }
                        break;
                    }

                    case (Net.EXIT): { // завершение сеанса связи
                        System.out.println("client disconnected");
                        synchronized (net) {
                            net.sendCommand(Net.EXIT);
                            net.exit();
                        }
                        workPr = false;
                        break;
                    }//неужели конец?!)
                }
            }

        } catch (SocketException e1) {
            System.out.println("клиент смотался, пинки!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

