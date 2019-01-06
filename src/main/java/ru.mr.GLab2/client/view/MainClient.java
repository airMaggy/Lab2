package ru.mr.GLab2.client.view;

import ru.mr.GLab2.client.view.version2.ClientModificator;
import ru.mr.GLab2.util.Net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MainClient {
    public static void main(String[] ar) {
        int serverPort = 6666;

        String address = "127.0.0.1"; // ip адрес сервера
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, serverPort);
            Net net = new Net(socket.getInputStream(), socket.getOutputStream());
            Thread t = new ClientModificator(net);
            t.start();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("Server not krutitcya");
        }
    }

}
