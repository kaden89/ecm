package ecm.web.dto.converters;

import ecm.dao.GenericDAO;
import ecm.model.*;
import ecm.web.dto.IncomingDTO;
import ecm.web.dto.OutgoingDTO;
import ecm.web.dto.TaskDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dkarachurin on 01.02.2017.
 */

public abstract class AbstractDocumentDTOConverterImpl<E, D> implements GenericDTOConverter<E, D> {
    @Inject
    private GenericDAO<Person> personDAO;

    public Collection<D> toDtoCollection(Collection<E> documents) {
        Collection<D> result = new ArrayList<>();
        for (E document : documents) {
            result.add(toDTO(document));
        }
        return result;
    }

    public Collection<E> fromDtoCollection(Collection<D> dtoCollection) {
        Collection<E> result = new ArrayList<>();
        for (D dto : dtoCollection) {
            result.add(fromDTO(dto));
        }
        return result;
    }

    public GenericDAO<Person> getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(GenericDAO<Person> personDAO) {
        this.personDAO = personDAO;
    }
}
