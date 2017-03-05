package ecm.web.dto.converters;

import ecm.model.Person;
import ecm.web.dto.PersonDTO;

import javax.inject.Singleton;

/**
 * Конвертер {@link Person} - {@link PersonDTO}
 * @author dkarachurin
 */
@Singleton
public class PersonDTOConverter extends AbstractStaffDTOConverterImpl<Person, PersonDTO> {
    @Override
    public Person fromDTO(PersonDTO dto) {
        Person person = new Person(dto);
        person.setPosition(getPostDAO().find(dto.getPositionId()));
        return person;
    }

    @Override
    public PersonDTO toDTO(Person staff) {
        return new PersonDTO(staff);
    }
}
