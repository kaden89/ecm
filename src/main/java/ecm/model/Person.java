package ecm.model;

import ecm.dao.Storable;
import ecm.web.dto.PersonDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@Entity
public class Person extends Staff implements Comparable<Person>, Storable {
    @NotNull
    private String firstname;
    @NotNull
    private String surname;
    @NotNull
    private String patronymic;
    @NotNull
    @ManyToOne
    private Post position;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "ownerId")
    private Image photo;
    @NotNull
    private LocalDate birthday;
    //Auto fill field for simplest sorting
    @NotNull
    private String fullname;


    public Person() {
    }

    public Person(String firstName, String surname, String patronymic, Post position, LocalDate birthday) {
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
        this.position = null;
        this.birthday = dto.getBirthday();
        this.fullname = dto.getFullname();
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

    public Post getPosition() {
        return position;
    }

    public void setPosition(Post position) {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @PostLoad
    @PrePersist
    private void calculateFullname() {
        this.fullname = firstname + " " + surname + " " + patronymic;
    }

    @Override
    public String toString() {
        return fullname;
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
