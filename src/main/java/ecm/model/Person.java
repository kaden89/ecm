package ecm.model;

import com.google.gson.annotations.SerializedName;
import ecm.dao.Storable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
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
    @Lob
    private byte[] photo;
    private LocalDate birthday;
    @Transient
    @SerializedName("name")
    private String fullName;

    public Person() {
    }

    public Person(String firstName, String surname, String patronymic, String position, byte[] photo, LocalDate birthday) {
        this.firstName = firstName;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
        this.photo = photo;
        this.birthday = birthday;
    }

    @PostLoad
    private void setFullName() {
        this.fullName = toString();
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


    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
