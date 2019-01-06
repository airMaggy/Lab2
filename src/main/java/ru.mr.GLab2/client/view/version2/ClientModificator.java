package ru.mr.GLab2.client.view.version2;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;
import ru.mr.GLab2.util.Net;
import ru.mr.GLab2.util.wrapper.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
Коды команд
Команды клиента
  1  0000 0001 получить автора    2  0000 0010 удалить автора      3  0000 0011 обновить автора     4  0000 0100 создать автора
129  1000 0001 получить книгу     130 1000 0010 удалить книгу      131 1000 0011 обновить книгу     132 1000 0100 создать книгу

  8  0000 1000 получить всех авторов
136  1000 1000 получить все книги
Команды наблюдателя
 10  0000 0101 оповещение об удалении автора       11 0000 0110 оповещение об изменении автора     12 0000 0111 оповещение о создании автора
138 1000 0101 оповещение об удалении книги        139 1000 0110 оповещение об изменении книги     140 1000 0111 оповещение о создании книги
Команды сервера
  0  0000 0000 отправление автора       9  0000 1001 отправление всех авторов
128  1000 0000 отправление книги      137  1000 1001 отправление всех книги
*/


public class ClientModificator extends Thread {
    private BasedV2 based;
    private NewAuthorV2 newAuthorV2;
    private NewBookV2 newBookV2;
    private Map<Long, OneOfAuthorV2> oneOfAuthorV2m;
    private Map<Long, OneOfBookV2> oneOfBookV2m;
    //  private Socket server;
    private ClientWriter clientWriter;
    private Net net;


    public ClientModificator(Net net) throws IOException {
        this.net = net;
        this.clientWriter = new ClientWriter(net);

        this.newAuthorV2 = null;//new NewAuthorV2();
        this.newBookV2 = null;//new NewBookV2();
//        this.oneOfAuthorV2s = new HashSet<>();
//        this.oneOfBookV2s = new HashSet<>();
        this.oneOfAuthorV2m = new HashMap<>();
        this.oneOfBookV2m = new HashMap<>();
    }

    @Override
    public void run() {
        based = new BasedV2(clientWriter, this);
        based.updateAuthorList();
        based.updateBookList();

        System.out.println("ClientModificator privet!");
        boolean workPr = true;
        CommandWrapper commandWrapper = new CommandWrapper();
        while (workPr) {
            int b = net.receiveCommand();
            System.out.println(commandWrapper.decodeCommand(b));

            switch (b) {

                case (Net.NOTIFY | Net.DELETE | Net.AUTHOR): {//оповещение об удалении автора
                    Long id = (Long) net.receiveObject();
                    based.removeAuthor(id);
                    break;
                }
                case (Net.NOTIFY | Net.DELETE | Net.BOOK): {//оповещение об удалении книги
                    Long id = (Long) net.receiveObject();
                    based.removeBook(id);
                    break;
                }

                case (Net.NOTIFY | Net.UPDATE | Net.AUTHOR): {//оповещение об изменении автора
                    Long id = (Long) net.receiveObject();
                    clientWriter.observerBasedChangeAuthor(id);
                    break;
                }
                case (Net.UPDATE | Net.AUTHOR | Net.BASED): {
                    Author author = (Author) net.receiveObject();
                    based.updateAuthor(author);
                    break;
                }

                case (Net.NOTIFY | Net.UPDATE | Net.BOOK): {//оповещение об изменении rybub
                    Long id = (Long) net.receiveObject();
                    clientWriter.observerBasedChangeBook(id);
                    break;
                }

                case (Net.UPDATE | Net.BOOK | Net.BASED): {
                    Book book = (Book) net.receiveObject();
                    based.updateBook(book);
                    break;
                }

                case (Net.NOTIFY | Net.CREATE | Net.AUTHOR): {//оповещение об создании автора
                    Long id = (Long) net.receiveObject();
                    clientWriter.observerBasedCreateAuthor(id);

                    break;
                }
                case (Net.CREATE | Net.AUTHOR | Net.BASED): {
                    Author author = (Author) net.receiveObject();
                    based.createAuthor(author);
                    break;
                }
                case (Net.NOTIFY | Net.CREATE | Net.BOOK): {//оповещение об создании rybub
                    Long id = (Long) net.receiveObject();
                    clientWriter.observerBasedCreateBook(id);
                    break;
                }
                case (Net.CREATE | Net.BOOK | Net.BASED): {
                    Book book = (Book) net.receiveObject();
                    based.createBook(book);
                    break;
                }

                case (Net.READ | Net.AUTHOR | Net.ONE): {//ONE OF AUTHOR GET AUTHOR
                    Author author = (Author) net.receiveObject();
                    oneOfAuthorV2m.get(author.getId()).fillAuthorByModificator(author);
                    break;
                }

                case (Net.CREATE | Net.AUTHOR | Net.NEW):
                case (Net.UPDATE | Net.AUTHOR | Net.NEW): {//NEW AUTHOR изменен автор
                    Long id = (Long) net.receiveObject();
                    if (id > -1) {
                        newAuthorV2.successCreateOrUpdateAuthor();
                        newAuthorV2 = null;
                    } else {
                        newAuthorV2.unsuccessCreateOrUpdateAuthor();
                    }
                    break;
                }
                case (Net.DELETE | Net.AUTHOR | Net.ONE): { //ONE OF AUTHOR це автор был удален, братки
                    Long id = (Long) net.receiveObject();
                    boolean ok = (boolean) net.receiveObject();
                    if (ok) {
                        oneOfAuthorV2m.get(id).successDeleteAuthor();
                    } else {
                        oneOfAuthorV2m.get(id).unsuccessDeleteAuthor();
                    }
                    break;// ujcgjlbgjvjubyfv
                }
                case (Net.READ | Net.AUTHOR | Net.NEW): { //NEW AUTHOR GET AUTHOR
                    Author author = (Author) net.receiveObject();
                    newAuthorV2.fillAuthorByModificator(author);
                    break;
                }

                case (Net.READ | Net.BOOK | Net.ONE): { //ONE OF BOOK GET BOOK
                    Book book = (Book) net.receiveObject();
                    oneOfBookV2m.get(book.getId()).fillBookByModificator(book);
                    break;
                }

                case (Net.CREATE | Net.BOOK | Net.NEW):
                case (Net.UPDATE | Net.BOOK | Net.NEW): { // NEWBOOK изменить книгу в бд

                    Long id = (Long) net.receiveObject();
                    if (id > -1) {
                        newBookV2.successCreateOrUpdateBook();
                        newBookV2 = null;
                    } else {
                        newBookV2.unsuccessCreateOrUpdateBook();
                    }
                    break;
                }
                case (Net.DELETE | Net.BOOK | Net.ONE): {// ONEOFBOOK удаление книги

                    Long id = (Long) net.receiveObject();
                    boolean ok = (boolean) net.receiveObject();
                    if (ok) {
                        oneOfBookV2m.get(id).successDeleteBook();
                    } else {
                        oneOfBookV2m.get(id).unsuccessDeleteBook();
                    }
                    break;
                }
                case (Net.READ | Net.BOOK | Net.NEW): { //NEW BOOK GET BOOK

                    Book book = (Book) net.receiveObject();
                    newBookV2.fillBookByModificator(book);
                    for (int i = 0; i < based.getAuthorListModel().getSize(); i++) {
                        newBookV2.addAuthorByModificator(based.getAuthorListModel().getElementAt(i));
                    }

                    break;
                }
                case (Net.READ | Net.ALL | Net.AUTHOR | Net.BASED): {//BASED отправлены все авторы
                    Long count = (Long) net.receiveObject(); //считываем количество переданных обьектов
                    System.out.println("количество авторов - " + count);
                    if (count > 0) {
                        based.removeAuthors();
                        for (int i = 0; i < count; i++) {
                            Author author = (Author) net.receiveObject();
                            System.out.println(author);
                            based.createAuthor(author);
                        }
                        based.revalidate();
                        based.repaint();
                    }
                    break;
                }
                case (Net.READ | Net.ALL | Net.BOOK | Net.BASED): {// BASED отпарвлены все книги

                    Long count = (Long) net.receiveObject();//считываем количество переданных обьектов
                    System.out.println("количество книг - " + count);
                    based.removeBooks();
                    if (count > 0) {
                        for (int i = 0; i < count; i++) {
                            Book book = (Book) net.receiveObject();
                            System.out.println(book);
                            based.createBook(book);
                        }
                        based.revalidate();
                        based.repaint();
                    }
                    break;
                }
                case (Net.EXIT): { //выход, все еще не знаю передаем
                    workPr = false;
                    net.exit();
                    break;
                }
            }

        }
    }

    /* private List<String> getEverythings() throws IOException {
         List<String> tempList = new ArrayList<>();
         while (true) {
             String data = in.readUTF();
             if (data.equals("stop")){
                 break;
             }
             tempList.add(data);
         }
         return tempList;
     }*/
    public void addOneOfAuthorV2(ClientWriter clientWr, Long id) throws IOException {
        if (!oneOfAuthorV2m.containsKey(id)) {
            oneOfAuthorV2m.put(id, new OneOfAuthorV2(clientWr, this, id));
            clientWr.oneOfAuthorGetAuthor(id);
        }
//        if (oneOfAuthorV2s.stream().noneMatch(a -> a.getId().equals(id))) {
//            System.out.println("Window is new: id = " + id);
//            this.oneOfAuthorV2s.add(new OneOfAuthorV2(clientWr, this, id));
//            clientWr.oneOfAuthorGetAuthor(id);
//        }
    }

    public void removeOneOfAuthorV2(OneOfAuthorV2 ooA) {
        oneOfAuthorV2m.remove(ooA.getId());
//        this.oneOfAuthorV2s.remove(ooA);
    }

    public void addOneOfBookV2(ClientWriter clientWr, Long id) throws IOException {
        if (!oneOfBookV2m.containsKey(id)) {
            oneOfBookV2m.put(id, new OneOfBookV2(clientWr, this, id));
            clientWr.oneOfBookGetBook(id);
        }
//        if (oneOfBookV2s.stream().noneMatch(a -> a.getId().equals(id))) {
//            OneOfBookV2 tmp = new OneOfBookV2(clientWr, this, id);
//            this.oneOfBookV2s.add(tmp);
//        }
    }

    public void removeOneOfBookV2(OneOfBookV2 ooB) {
        oneOfBookV2m.remove(ooB.getId());
//        this.oneOfBookV2s.remove(ooB);
    }

    public void createNewAuthorV2(ClientWriter clientWr, Long id) throws IOException {
        this.newAuthorV2 = new NewAuthorV2(clientWr, id);//переписать формы((
        if (id > -1) {
            clientWr.newAuthorGetAuthor(id);
        }
    }

    public void deleteNewAuthorV2() {
        this.newAuthorV2 = null;
    }

    public void deleteNewBookV2() {
        this.newBookV2 = null;
    }

    public void createNewBookV2(ClientWriter clientWr, Long id) throws IOException {
        this.newBookV2 = new NewBookV2(clientWr, id);
        if (id < 0) {
            for (int i = 0; i < based.getAuthorListModel().getSize(); i++) {
                newBookV2.addAuthorByModificator(based.getAuthorListModel().getElementAt(i));
            }
        }
        if (id > -1) {
            clientWr.newBookGetBook(id);
        }
    }

}
