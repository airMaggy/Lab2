package ru.mr.GLab2.util;

import java.io.*;
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
    private int bufferSize = 1024;
    private boolean busy;

    public Net(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        this.busy = false;
    }

    public boolean isBusy() {
        return busy;
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

    public String decodeCommand(int command) {
        StringBuilder sb = new StringBuilder();
        if ((command & Net.CREATE) != 0) {
            sb.append("CREATE ");
        }
        if ((command & Net.READ) != 0) {
            sb.append("READ ");
        }
        if ((command & Net.UPDATE) != 0) {
            sb.append("UPDATE ");
        }
        if ((command & Net.DELETE) != 0) {
            sb.append("DELETE ");
        }
        if ((command & Net.AUTHOR) != 0) {
            sb.append("AUTHOR ");
        }
        if ((command & Net.BOOK) != 0) {
            sb.append("BOOK ");
        }
        if ((command & Net.ALL) != 0) {
            sb.append("ALL ");
        }
        if ((command & Net.NOTIFY) != 0) {
            sb.append("NOTIFY ");
        }
        if ((command & Net.BASED) != 0) {
            sb.append("BASED ");
        }
        if ((command & Net.NEW) != 0) {
            sb.append("NEW ");
        }
        if ((command & Net.ONE) != 0) {
            sb.append("ONE ");
        }
        if ((command & Net.EXIT) != 0) {
            sb.append("EXIT ");
        }
        return sb.toString();
    }

    public void sendCommand(int command) {
        try {
            new DataOutputStream(out).writeInt(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int receiveCommand() {
        try {
            return new DataInputStream(in).readInt();
        } catch (StreamCorruptedException e1) {
            System.out.println("пинки, пришёл не инт, вот такая хреновина");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void sendObject(Object object) {
        try {
            new ObjectOutputStream(out).writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);

            byte[] objectByte = byteArrayOutputStream.toByteArray();
            int objectSize = objectByte.length;

            out.write(objectSize);
            out.flush();
            System.out.println("Send size: " + objectSize);
            for (int offset = 0; offset < objectSize; offset += bufferSize) {
                int length = bufferSize;
                if (offset + length > objectSize) {
                    length = objectSize - offset;
                }
                System.out.println("Send: [" + offset + "; " + (offset + length) + ")");
                out.write(objectByte, offset, length);
                out.flush();
                if (in.read() == Net.OK) {
                    System.out.println("Send " + (offset / bufferSize) + " part");
                } else {
                    System.out.println("Some problems...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public Object receiveObject() {
        Object object = 0;
        try {
            object = new ObjectInputStream(in).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
        /*
        Object object = null;
        try {
            byte[] buffer = new byte[bufferSize];
            int read;
            int all = in.read();
            if (all == -1) {
                return 0;
            }
            System.out.println("Receive size: " + all);
            byte[] objectByte = new byte[all];
            int offset = 0;
            while ((read = in.read(buffer)) > -1) {
                out.write(Net.OK);
                out.flush();
                System.out.println(offset + " " + read + " " + all);
                all -= read;
                System.arraycopy(buffer, 0, objectByte, offset, read);
                System.out.println("Receive: [" + offset + "; " + (offset + read) + ")");
                offset += read;
                if (all <= 0) {
                    break;
                }
            }
            object = new ObjectInputStream(new ByteArrayInputStream(objectByte)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
        */
    }
}
