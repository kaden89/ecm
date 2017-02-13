package ecm.web.dto;

import ecm.model.Person;
import ecm.web.dto.PersonDTO;

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
public class PersonsDTO {
    @XmlElement(name = "person", type = PersonDTO.class)
    private List<PersonDTO> persons = new ArrayList<>();

    public PersonsDTO() {
    }

    public PersonsDTO(List<PersonDTO> persons) {
        this.persons = persons;
    }

    public List<PersonDTO> getPersons() {
        return persons;
    }

}
