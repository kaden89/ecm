package ecm.util.xml;

import ecm.model.Person;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 11.01.2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "persons")
public class Persons {
    @XmlElement(name = "person", type = Person.class)
    private List<Person> persons = new ArrayList<>();

    public Persons() {
    }

    public Persons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

}
