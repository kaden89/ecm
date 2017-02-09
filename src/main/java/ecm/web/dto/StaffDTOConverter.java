package ecm.web.dto;

import ecm.dao.GenericDAO;
import ecm.dao.GenericDaoJpa;
import ecm.model.Person;
import ecm.model.Post;
import ecm.model.Staff;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dkarachurin on 02.02.2017.
 */
@Singleton
public class StaffDTOConverter implements DTOConverter<Staff, AbstractStaffDTO> {
    @Inject
    private GenericDAO<Post> postDAO;

    public Staff fromDTO(AbstractStaffDTO dto) {
        if (dto instanceof PersonDTO) {
            Person person = new Person((PersonDTO) dto);
            person.setPosition(postDAO.find(((PersonDTO) dto).getPositionId()));
            return person;
        }
        return null;
    }

    public AbstractStaffDTO toDTO(Staff staff) {
        if (staff instanceof Person) {
            return new PersonDTO((Person) staff);
        }
        return null;
    }

    public Collection<AbstractStaffDTO> toDtoCollection(Collection<Staff> staffs) {
        Collection<AbstractStaffDTO> result = new ArrayList<>();
        for (Staff staff : staffs) {
            result.add(toDTO(staff));
        }
        return result;
    }

    public Collection<Staff> fromDtoCollection(Collection<AbstractStaffDTO> dtoCollection) {
        Collection<Staff> result = new ArrayList<>();
        for (AbstractStaffDTO dto : dtoCollection) {
            result.add(fromDTO(dto));
        }
        return result;
    }

}
