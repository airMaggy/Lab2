package ru.mr.GLab2.client.view.version2;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class BasedV2 extends JFrame {

    private JList bookList;
    private JList authorList;
    private JButton newBookButton;
    private JButton newAuthorButton;
    private JPanel mainPanel;
    private DefaultListModel<Book> bookListModel;
    private DefaultListModel<Author> authorListModel;
    private ClientWriter clientWr;
    //   private ClientModificator clientMod;


    public BasedV2(ClientWriter clientWr, ClientModificator clientModificator) {
        this.clientWr = clientWr;

        bookListModel = new DefaultListModel<>();
        authorListModel = new DefaultListModel<>();

        $$$setupUI$$$();
        bookList.setModel(bookListModel);
        authorList.setModel(authorListModel);


        getContentPane().add(mainPanel); //Добавить панель с содержимым на форму
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //При закрытии окна останавливать программу
        pack(); //Подогнать размеры окна под содержимое
        setVisible(true); //показать форму
        this.revalidate();
        this.repaint();


        newBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientModificator.createNewBookV2(clientWr, -1L);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        newAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientModificator.createNewAuthorV2(clientWr, -1L);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException e2) {
                    System.out.println("this is nothing");
                }
            }
        });

        authorList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        Author tmp = (Author) authorList.getSelectedValue();
                        clientModificator.addOneOfAuthorV2(clientWr, tmp.getId());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (NullPointerException e2) {
                        System.out.println("this is nothing");
                    }
                }

            }

        });
        bookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        Book tmp = (Book) bookList.getSelectedValue();
                        //            System.out.println(tmp);
                        Long id = tmp.getId();
                        System.out.println(id);

                        clientModificator.addOneOfBookV2(clientWr, id);
                        //                      new OneOfBookV2(clientWr, id);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (NullPointerException e2) {
                        System.out.println("books also not exist");
                    }
                }

            }
        });

    }

    public DefaultListModel<Book> getBookListModel() {
        return bookListModel;
    }

    public DefaultListModel<Author> getAuthorListModel() {
        return authorListModel;
    }

    public JList getBookList() {
        return bookList;
    }

    public JList getAuthorList() {
        return authorList;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            clientWr.exitClient();
        }
        super.processWindowEvent(e);
    }

    //два метода, чтоб обновить лист авторов
    public void updateAuthorList() {
        clientWr.basedGetAuthors();
    }

    public void getAuthorsListByModificator(List<Author> authorsModList) {//господипрости | Нет. УХАХАХАХАХАХА
        authorListModel.removeAllElements();
        authorsModList.forEach(authorListModel::addElement);
    }

    //два метода, чтоб обновить лист книг
    public void updateBookList() {
        clientWr.basedGetBooks();
    }

    public void getBooksListByModificator(List<Book> booksModList) {
        bookListModel.removeAllElements();
        booksModList.forEach(bookListModel::addElement);
    }

    public void removeAuthor(Long id) {
        for (int i = 0; i < authorListModel.getSize(); i++) {
            Author k = authorListModel.get(i);
            if (k.getId().equals(id)) {
                authorListModel.remove(i);
                break;
            }
        }
//        authorList.updateUI();
    }

    public void removeBook(Long id) {
        for (int i = 0; i < bookListModel.getSize(); i++) {
            Book k = bookListModel.get(i);
            if (k.getId().equals(id)) {
                bookListModel.remove(i);
                break;
            }
        }
//        bookList.updateUI();
    }

    public void createAuthor(Author author) {
        //    StringBuilder builder = new StringBuilder(author.getId().toString());
        //     builder.append(", ").append(author.getNameAuthor()).append(", ").append(author.getHappyYear().toString());
        authorListModel.addElement(author);
//        authorList.updateUI();
    }

    public void createBook(Book book) {
        //       StringBuilder builder = new StringBuilder(book.getId().toString());
        //      builder.append(", ").append(book.getHappyYear().toString());
        bookListModel.addElement(book);
        //      bookList.updateUI();
    }

    public void updateAuthor(Author author) {
        removeAuthor(author.getId());
        createAuthor(author);
        //     authorList.updateUI();
    }

    public void updateBook(Book book) {
        removeBook(book.getId());
        createBook(book);
        //      bookList.updateUI();
    }

    public void removeAuthors() {

        authorListModel.removeAllElements();
//        authorList.updateUI();
    }

    public void removeBooks() {

        bookListModel.removeAllElements();
//        bookList.updateUI();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        bookList = new JList();
        bookList.setSelectionMode(0);
        mainPanel.add(bookList, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        authorList = new JList();
        authorList.setSelectionMode(0);
        mainPanel.add(authorList, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Books");
        mainPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Authors");
        mainPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newAuthorButton = new JButton();
        newAuthorButton.setText("Add Author");
        mainPanel.add(newAuthorButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newBookButton = new JButton();
        newBookButton.setText("Add Book");
        mainPanel.add(newBookButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("New Book");
        mainPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("New Author");
        mainPanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHEAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
