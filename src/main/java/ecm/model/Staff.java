package ecm.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by dkarachurin on 11.01.2017.
 */
@XmlSeeAlso({Person.class, Department.class, Organization.class})
@MappedSuperclass
public abstract class Staff {
    @Id
    private int id;

    public Staff() {
    }

    public Staff(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
