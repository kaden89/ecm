package ecm.web.dto;

import ecm.model.Person;

import java.time.LocalDate;

/**
 * ДТО {@link Person}
 * @author dkarachurin
 */
public class PersonDTO extends AbstractStaffDTO {
    private String firstname;
    private String surname;
    private String patronymic;
    private Integer positionId;
    private String positionName;
    private LocalDate birthday;
    private String fullname;
    private String type = "person";

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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return firstname + " " + surname + " " + patronymic;
    }
}
