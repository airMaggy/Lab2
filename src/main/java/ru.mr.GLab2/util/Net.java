package ru.mr.GLab2.util;

import ru.mr.GLab2.util.wrapper.BooleanWrapper;
import ru.mr.GLab2.util.wrapper.CommandWrapper;
import ru.mr.GLab2.util.wrapper.LongWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//а за этот код спасибо богу проги и безопасности ckfdfwfhz[hfyb
public class Net {
    public static final int CREATE = 0b1000_0000_0000;
    public static final int READ = 0b0100_0000_0000;
    public static final int UPDATE = 0b0010_0000_0000;
    public static final int DELETE = 0b0001_0000_0000;

    public static final int AUTHOR = 0b0000_0001_0000;
    public static final int BOOK = 0b0000_0010_0000;
    public static final int ALL = 0b0000_0100_0000;

    public static final int NOTIFY = 0b0000_0000_1000;
    public static final int BASED = 0b0000_0000_0001;
    public static final int NEW = 0b0000_0000_0010;
    public static final int ONE = 0b0000_0000_0100;

    public static final int OK = 0;

    public static final int EXIT = 0b0000_1000_0000; //TEMPORARY

    private InputStream in;
    private OutputStream out;

    public Net(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public boolean exit() {
        try {
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendCommand(int command) {
        Xml.uploadToSocket(command, out);
    }

    public int receiveCommand() {
        return ((CommandWrapper) Xml.downloadFromSocket(in)).getCode();
    }

    public void sendObject(Object object) {
        Xml.uploadToSocket(object, out);
    }

    public Object receiveObject() {
        Object object = Xml.downloadFromSocket(in);
        if (object instanceof LongWrapper) {
            return ((LongWrapper) object).getValue();
        }
        if (object instanceof BooleanWrapper) {
            return ((BooleanWrapper) object).getValue();
        }
        return object;
    }
}
