package ru.mr.GLab2.client.view;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] ar) throws IOException {
        int serverPort = 6666;
        String address = "127.0.0.1"; // ip адрес сервера

        InetAddress ipAddress = InetAddress.getByName(address);
        Socket socket = new Socket(ipAddress, serverPort);


    }
}
