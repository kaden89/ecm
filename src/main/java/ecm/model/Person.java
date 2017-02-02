package ecm.model;

import ecm.dao.Storable;
import ecm.util.xml.GsonExclude;
import ecm.util.xml.LocalDateAdapter;
import ecm.web.dto.PersonDTO;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@XmlRootElement
@Entity
public class Person extends Staff implements Comparable<Person>, Storable{

    private String firstname;
    private String surname;
    private String patronymic;
    private String position;
    @XmlTransient
    @GsonExclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "owner")
    private Image photo;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate birthday;

    public Person() {
    }

    public Person(String firstName, String surname, String patronymic, String position, LocalDate birthday) {
        this.firstname = firstName;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
        this.birthday = birthday;
    }

    public Person(PersonDTO dto) {
        this.setId(dto.getId());
        this.firstname = dto.getFirstname();
        this.surname = dto.getSurname();
        this.patronymic = dto.getPatronymic();
        this.position = dto.getPosition();
        this.birthday = dto.getBirthday();
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return firstname +
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
