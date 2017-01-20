package ecm.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by dkarachurin on 11.01.2017.
 */
@XmlSeeAlso({Person.class, Department.class, Organization.class})
@XmlAccessorType(XmlAccessType.FIELD)
@MappedSuperclass
public abstract class Staff {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Staff() {
    }

    public Staff(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
