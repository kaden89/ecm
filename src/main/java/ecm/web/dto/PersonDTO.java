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
    private Integer positionId;
    private String positionName;
    private LocalDate birthday;
    private String fullname;
    private String restUrl = "persons";

    public PersonDTO() {
    }

    public PersonDTO(Person person) {
        super(person.getId());
        this.firstname = person.getFirstname();
        this.surname = person.getSurname();
        this.patronymic = person.getPatronymic();
        this.positionId = person.getPosition().getId();
        this.positionName = person.getPosition().getPost();
        this.birthday = person.getBirthday();
        this.fullname = person.getFullname();
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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer position) {
        this.positionId = position;
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

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
