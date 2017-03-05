package ecm.web.dto.converters;

import ecm.dao.GenericDAO;
import ecm.model.Person;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Абстарктная реализация конвертера документов: ДТО-Объект
 * @see IncomingDTOConverter
 * @see OutgoingDTOConverter
 * @see TaskDTOConverter
 * @author dkarachurin
 */

public abstract class AbstractDocumentDTOConverterImpl<T, D> implements GenericDTOConverter<T, D> {
    @Inject
    private GenericDAO<Person> personDAO;

    public AbstractDocumentDTOConverterImpl() {
    }

    public Collection<D> toDtoCollection(Collection<T> documents) {
        Collection<D> result = new ArrayList<>();
        for (T document : documents) {
            result.add(toDTO(document));
        }
        return result;
    }

    public Collection<T> fromDtoCollection(Collection<D> dtoCollection) {
        Collection<T> result = new ArrayList<>();
        for (D dto : dtoCollection) {
            result.add(fromDTO(dto));
        }
        return result;
    }

    public GenericDAO<Person> getPersonDAO() {
        return personDAO;
    }
}
