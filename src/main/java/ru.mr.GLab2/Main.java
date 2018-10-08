package ru.mr.GLab2;

import ru.mr.GLab2.client.view.Based;
import ru.mr.GLab2.server.controller.Controller;
import ru.mr.GLab2.server.model.ComboModel;

import javax.swing.*;

public class Main {
    public static void main(final String[] args) throws Exception {
        ComboModel CM = new ComboModel();
        Controller one = new Controller(CM);

        /*Book two = new Book();
        one.createBook(two);
        Author three=new Author(1L,"rrr",20L,30L,"rrr","trewsd");
        //public Author (Long id, String nameAuthor, Long happyYear, Long sadYear, String happyPlace, String interestingFacts)
        one.createAuthor(three);
        CM.setSerilCM();
        System.out.print(two);
        for (Author author : CM.getAuthors()) {
            System.out.println(author.getNameAuthor() + " " + author.getHappyYear());
        }*/

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Based(CM, one);
            }
        });
        //Based tmp=new Based();
        //JFrame tmp = new JFrame();
        // tmp.setContentPane(Based);
        //tmp.setVisible(true);

    }
}
