package ecm.web.dto;

import ecm.model.Person;

import java.time.LocalDate;

/**
 * Created by dkarachurin on 02.02.2017.
 */
public class PersonDTO extends AbstractStaffDTO {
    private String firstname;
    private String surname;
    private String patronymic;
    private String position;
    private LocalDate birthday;
    private String fullname;

    public PersonDTO() {
    }

    public PersonDTO(Person person) {
        super(person.getId());
        this.firstname = person.getFirstname();
        this.surname = person.getSurname();
        this.patronymic = person.getPatronymic();
        this.position = person.getPosition();
        this.birthday = person.getBirthday();
        this.fullname = person.toString();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
