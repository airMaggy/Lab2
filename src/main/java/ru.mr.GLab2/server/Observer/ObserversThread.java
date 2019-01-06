package ru.mr.GLab2.server.Observer;

import ru.mr.GLab2.util.Net;

/*
Коды команд
Команды клиента
1  0001 получить автора    2  0010 удалить автора     3  0011 обновить автора    4  0100 создать автора
9  1001 получить книгу     10 1010 удалить книгу      11 1011 обновить книгу     12 1100 создать книгу
Команды наблюдателя
5  0101 оповещение об удалении автора      6  0110 оповещение об изменении автора     7  0111 оповещение о создании автора
13 1101 оповещение об удалении книги       14 1110 оповещение об изменении книги      15 1111 оповещение о создании книги
Команды сервера
0  0000 отправление автора
8  1000 отправление книги
*/
public class ObserversThread implements Observer {
    private ObservableController observableController;
    private Net net;

    public ObserversThread(Net net, ObservableController observableController) {
        this.net = net;
        this.observableController = observableController;
        this.observableController.registerObserver(this);

    }

    @Override
    synchronized public void createAuthorData(Long id) {
        net.sendCommand(Net.NOTIFY | Net.CREATE | Net.AUTHOR);
        net.sendObject(id);
    }

    @Override
    synchronized public void changeAuthorData(Long id) {

        net.sendCommand(Net.NOTIFY | Net.UPDATE | Net.AUTHOR);
        net.sendObject(id);
    }

    @Override
    synchronized public void deleteAuthorData(Long id) {

        net.sendCommand(Net.NOTIFY | Net.DELETE | Net.AUTHOR);
        net.sendObject(id);
    }

    @Override
    synchronized public void createBookData(Long id) {

        net.sendCommand(Net.NOTIFY | Net.CREATE | Net.BOOK);
        net.sendObject(id);
    }

    @Override
    synchronized public void changeBookData(Long id) {

        net.sendCommand(Net.NOTIFY | Net.UPDATE | Net.BOOK);
        net.sendObject(id);
    }

    @Override
    synchronized public void deleteBookData(Long id) {

        net.sendCommand(Net.NOTIFY | Net.DELETE | Net.BOOK);
        net.sendObject(id);
    }
}
