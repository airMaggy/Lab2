package ru.mr.GLab2.client.view.version2;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class NewBookV2 extends JFrame {
    private JTextField nameField;
    private JTextField yearField;
    private JTextField genreField;
    private JTextField pagesField;
    private JList allAuthorsList;
    private DefaultListModel<Author> allAuthorsListModel;
    private JList booksAuthorsList;
    private DefaultListModel<Author> booksAuthorsListModel;
    private JButton createButton;
    private JLabel mainLabel;
    private JPanel panel1;
    private JLabel errorLabel;
    private long id;
//    private ClientWriter clientWr;

    public NewBookV2(ClientWriter clientWr, long id) throws IOException {
        this.id = id;
        //       this.clientWr = clientWr;
        $$$setupUI$$$();

        allAuthorsListModel = new DefaultListModel<>();
        booksAuthorsListModel = new DefaultListModel<>();
        allAuthorsList.setModel(allAuthorsListModel);
        booksAuthorsList.setModel(booksAuthorsListModel);
        if (id > -1) {
            mainLabel.setText("Edit Book");
            createButton.setText("Update");
//            String[] fields = client.getFieldBookById(id).split("\\|");
//            nameField.setText(fields[1]);
//            yearField.setText(fields[2]);
//            genreField.setText(fields[3]);
//            pagesField.setText(fields[4]);
//            client.getAuthorsBookById(id).forEach(booksAuthorsListModel::addElement);
//            client.getAuthors().stream().filter(author -> !booksAuthorsListModel.contains(author)).forEach(allAuthorsListModel::addElement);
            clientWr.newBookGetBook(id);
        } else {
            mainLabel.setText("New Book");
        }

        getContentPane().add(panel1); //Добавить панель с содержимым на форму
        pack(); //Подогнать размеры окна под содержимое
        setVisible(true); //показать форму

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isCorrect = true;
                long pages = 0;
                long year = 0;
                try {
                    pages = Long.valueOf(pagesField.getText());
                    year = Long.valueOf(yearField.getText());
                } catch (NumberFormatException e1) {
                    errorLabel.setText("Long or live?");
                    isCorrect = false;
                }
                if (!booksAuthorsListModel.isEmpty() && isCorrect) {

                    Book tempBook = new Book();
                    if (id > -1) {
                        tempBook.setId(id);
                    }
                    tempBook.setNameBook(nameField.getText());
                    tempBook.setHappyYear(year);
                    tempBook.setPages(pages);
                    tempBook.setGenre(genreField.getText());
                    Set<Author> tempAuths = new HashSet<>();
                    for (int i = 0; i < booksAuthorsListModel.getSize(); i++) {
                        tempAuths.add(booksAuthorsListModel.getElementAt(i));
                    }
                    tempBook.setAuthors(tempAuths);
                    if (id > -1) {
                        clientWr.newBookUpdateBook(tempBook);
                    } else {
                        clientWr.newBookCreateBook(tempBook);
                    }

                } else {
                    errorLabel.setText("Incorrect data");
                }
            }
        });
        allAuthorsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Author tmp = (Author) allAuthorsList.getSelectedValue();
                    booksAuthorsListModel.addElement(tmp);
                    allAuthorsListModel.removeElement(allAuthorsList.getSelectedValue());
                }
            }
        });
        booksAuthorsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Author tmp = (Author) booksAuthorsList.getSelectedValue();
                    allAuthorsListModel.addElement(tmp);
                    booksAuthorsListModel.removeElement(booksAuthorsList.getSelectedValue());
                }
            }
        });
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
 /*       if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            try {
                if (id > -1)
                    clientWr.setFreeBookById(id);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }*/
        super.processWindowEvent(e);
    }

    public void fillBookByModificator(Book book) {
        nameField.setText(book.getNameBook());
        yearField.setText(book.getHappyYear().toString());
        genreField.setText(book.getGenre());
        pagesField.setText(book.getPages().toString());
        book.getAuthors().forEach(booksAuthorsListModel::addElement);
 //       this.update(this.getGraphics());
    }

    public void addAuthorByModificator(Author author) {

        for (int i = 0; i < booksAuthorsListModel.getSize(); i++) {
            if (booksAuthorsListModel.getElementAt(i).equals(author)) {
                return;
            }
        }
        allAuthorsListModel.addElement(author);
 //       allAuthorsList.updateUI();
    }

    public void successCreateOrUpdateBook() {

        dispose();
    }

    public void unsuccessCreateOrUpdateBook() {
        errorLabel.setText("Error addElement in BD, lalala");
        pack();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Name");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Year");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Genre");
        panel1.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Pages");
        panel1.add(label4, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameField = new JTextField();
        panel1.add(nameField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        yearField = new JTextField();
        panel1.add(yearField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        genreField = new JTextField();
        panel1.add(genreField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        pagesField = new JTextField();
        panel1.add(pagesField, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        booksAuthorsList = new JList();
        panel1.add(booksAuthorsList, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        createButton = new JButton();
        createButton.setText("Create");
        panel1.add(createButton, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainLabel = new JLabel();
        mainLabel.setText("New Book");
        panel1.add(mainLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        allAuthorsList = new JList();
        panel1.add(allAuthorsList, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        errorLabel = new JLabel();
        errorLabel.setText("");
        panel1.add(errorLabel, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
