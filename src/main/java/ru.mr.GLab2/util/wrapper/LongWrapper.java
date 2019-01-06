package ru.mr.GLab2.util.wrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "long")
@XmlType(name = "long")
public class LongWrapper {
    private Long value;

    public LongWrapper() {
        this.value = 0L;
    }

    public LongWrapper(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @XmlElement
    public void setValue(Long value) {
        this.value = value;
    }
}
