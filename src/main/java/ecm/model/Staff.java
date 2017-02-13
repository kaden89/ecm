package ecm.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by dkarachurin on 11.01.2017.
 */
@MappedSuperclass
@Access(value = AccessType.FIELD)
public abstract class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;

    public Staff() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
