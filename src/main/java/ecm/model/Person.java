package ecm.model;

import ecm.dao.Storable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@XmlRootElement
@Entity
public class Person extends Staff implements Comparable<Person>, Storable{
    private String firstName;
    private String surname;
    private String patronymic;
    private String position;
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private  List<Incoming> incomings;
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private  List<Outgoing> outgoings;
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private  List<Task> tasks;
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "executor")
    private  List<Task> tasksExecutor;
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "controller")
    private  List<Task> tasksController;
    @XmlTransient
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "recipient")
    private  List<Outgoing> recipient;
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
    private  List<Incoming> recipientInc;
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private  List<Incoming> senderInc;
    public Person() {
    }

    public Person(int id, String firstName, String surname, String patronymic, String position) {
        super(id);
        this.firstName = firstName;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return firstName  +
                " " + surname +
                " " + patronymic;
    }

    @Override
    public int compareTo(Person person) {
        return this.toString().compareTo(person.toString());
    }

    @Override
    public String getStorageName() {
        return "Person";
    }


}
