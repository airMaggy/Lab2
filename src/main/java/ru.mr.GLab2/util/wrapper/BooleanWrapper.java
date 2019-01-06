package ru.mr.GLab2.util.wrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "boolean")
@XmlType(name = "boolean")
public class BooleanWrapper {
    private Boolean value;

    public BooleanWrapper() {
        this.value = false;
    }

    public BooleanWrapper(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @XmlElement(required = true)
    public void setValue(Boolean value) {
        this.value = value;
    }
}
