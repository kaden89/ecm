package ecm.model;

import ecm.dao.Storable;
import ecm.web.dto.PersonDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Модель Сотрудника
 * @author dkarachurin
 */
@Entity
public class Person extends Staff implements Comparable<Person>, Storable {
    @Size(max = 255)
    private String firstname;
    @Size(max = 255)
    private String surname;
    @Size(max = 255)
    private String patronymic;
    @NotNull
    @ManyToOne
    private Post position;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "ownerId")
    private Avatar photo;
    @NotNull
    private LocalDate birthday;
    //Auto fill field for simplest sorting
    @Size(max = 1024)
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
        this(dto.getFirstname(), dto.getSurname(), dto.getPatronymic(), null, dto.getBirthday());
        this.setId(dto.getId());
        this.setFullname(dto.toString());
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

    public Avatar getPhoto() {
        return photo;
    }

    public void setPhoto(Avatar photo) {
        this.photo = photo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Собирает поле fullname перед сохранением в БД
     */
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
