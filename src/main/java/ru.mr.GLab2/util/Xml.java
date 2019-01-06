package ru.mr.GLab2.util;

import ru.mr.GLab2.server.model.Author;
import ru.mr.GLab2.server.model.Book;
import ru.mr.GLab2.util.wrapper.BooleanWrapper;
import ru.mr.GLab2.util.wrapper.CommandWrapper;
import ru.mr.GLab2.util.wrapper.LongWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class Xml {
    public static void uploadToSocket(Object object, OutputStream out) {
        try {
            JAXBContext context = JAXBContext.newInstance(Author.class, Book.class, CommandWrapper.class, LongWrapper.class, BooleanWrapper.class);
            if (object instanceof Integer) {
                object = new CommandWrapper((Integer) object);
            }
            if (object instanceof Long) {
                object = new LongWrapper((Long) object);
            }
            if (object instanceof Boolean) {
                object = new BooleanWrapper((Boolean) object);
            }
            StringWriter stringWriter = new StringWriter();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, stringWriter);
            new DataOutputStream(out).writeUTF(stringWriter.toString());
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Object downloadFromSocket(InputStream in) {
        Object object = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Author.class, Book.class, CommandWrapper.class, LongWrapper.class, BooleanWrapper.class);
            String xml = new DataInputStream(in).readUTF();
            StringReader stringReader = new StringReader(xml);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            object = unmarshaller.unmarshal(stringReader);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}
