package ecm.web.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * ДТО коллекции {@link ecm.model.Person}
 * @author dkarachurin
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
