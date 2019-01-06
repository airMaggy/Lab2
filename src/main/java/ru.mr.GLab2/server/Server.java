package ru.mr.GLab2.server;

import ru.mr.GLab2.server.controller.Controller;
import ru.mr.GLab2.server.model.ComboModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {


    public static void main(String[] ar) throws IOException, ClassNotFoundException {
        ComboModel model = new ComboModel();
        Controller one = new Controller(model);

        int port = 6666;
   //     int port1=6667;
        try {
            ServerSocket ss = new ServerSocket(port);
            //     ServerSocket ss1 =new ServerSocket(port1);

            while (true) {
                System.out.print("Wait...");
                Socket socket = ss.accept();//посмотреть че делает аццепт
                //        Socket socket1 = ss1.accept();
                System.out.print("Someone connected!");
                Thread t = new MonoThreadClientReader(socket, model, one);
                t.start();
                //ждем у моря погоды
            }
        }
        catch (SocketException e1) {
            System.out.println("Connection reset, tsss");
        }

    }

}
