package ecm.web.dto.converters;

import ecm.model.Person;
import ecm.web.dto.PersonDTO;

import javax.inject.Singleton;

/**
 * Created by dkarachurin on 13.02.2017.
 */
@Singleton
public class PersonDTOConverter extends AbstractStaffDTOConverterImpl<Person, PersonDTO> {
}
