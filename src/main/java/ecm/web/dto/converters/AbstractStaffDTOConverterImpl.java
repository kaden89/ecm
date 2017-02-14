package ecm.web.dto.converters;

import ecm.dao.GenericDAO;
import ecm.model.Person;
import ecm.model.Post;
import ecm.web.dto.PersonDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dkarachurin on 02.02.2017.
 */

public abstract class AbstractStaffDTOConverterImpl<E, D> implements GenericDTOConverter<E, D> {
    @Inject
    private GenericDAO<Post> postDAO;

    public Collection<D> toDtoCollection(Collection<E> staffs) {
        Collection<D> result = new ArrayList<>();
        for (E staff : staffs) {
            result.add(toDTO(staff));
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

    public GenericDAO<Post> getPostDAO() {
        return postDAO;
    }

    public void setPostDAO(GenericDAO<Post> postDAO) {
        this.postDAO = postDAO;
    }
}
