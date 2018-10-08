package ru.mr.GLab2.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] ar) throws IOException {
        int port = 6666;
        ServerSocket ss = new ServerSocket(port);

        Socket soket = ss.accept();//monitorim
        //потоки для передачи данных
        InputStream sin = soket.getInputStream();
        OutputStream sout = soket.getOutputStream();
        //обертки
        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);

        while (true) {
            //чет непонятно пока???
        }

    }

}
