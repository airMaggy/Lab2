package ru.mr.GLab2.util.wrapper;

import ru.mr.GLab2.util.Net;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "command")
@XmlType(name = "command")
public class CommandWrapper {
    private int code;
    private String value;

    public CommandWrapper() {
        this.code = Net.OK;
        this.value = decodeCommand(this.code);
    }

    public CommandWrapper(int code) {
        this.code = code;
        this.value = decodeCommand(this.code);
    }

    public int getCode() {
        return code;
    }

    @XmlElement(required = true)
    public void setCode(int code) {
        this.code = code;
        this.value = decodeCommand(this.code);
    }

    public String getValue() {
        return value;
    }

    @XmlElement(required = true)
    public void setValue(String value) {
        this.value = value;
        this.code = encodeCommand(this.value);
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
        return sb.toString().trim();
    }

    public int encodeCommand(String command) {
        int code = 0;
        if (command.contains("CREATE")) {
            code += Net.CREATE;
        }
        if (command.contains("READ")) {
            code += Net.READ;
        }
        if (command.contains("UPDATE")) {
            code += Net.UPDATE;
        }
        if (command.contains("DELETE")) {
            code += Net.DELETE;
        }
        if (command.contains("AUTHOR")) {
            code += Net.AUTHOR;
        }
        if (command.contains("BOOK")) {
            code += Net.BOOK;
        }
        if (command.contains("ALL")) {
            code += Net.ALL;
        }
        if (command.contains("NOTIFY")) {
            code += Net.NOTIFY;
        }
        if (command.contains("BASED")) {
            code += Net.BASED;
        }
        if (command.contains("NEW")) {
            code += Net.NEW;
        }
        if (command.contains("ONE")) {
            code += Net.ONE;
        }
        if (command.contains("EXIT")) {
            code += Net.EXIT;
        }
        return code;
    }
}
