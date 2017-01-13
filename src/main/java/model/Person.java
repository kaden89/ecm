package model;

import dao.Storable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@XmlRootElement
@Entity
@Table(name = "Persons")
public class Person extends Staff implements Comparable<Person>, Storable{
    private String firstName;
    private String surname;
    private String patronymic;
    private String position;

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

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    @XmlElement
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    @XmlElement
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    @XmlElement
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
