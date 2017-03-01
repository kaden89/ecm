package ecm.web.dto.converters;

import ecm.dao.GenericDAO;
import ecm.model.Post;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dkarachurin on 02.02.2017.
 */

public abstract class AbstractStaffDTOConverterImpl<T, D> implements GenericDTOConverter<T, D> {
    @Inject
    private GenericDAO<Post> postDAO;

    public AbstractStaffDTOConverterImpl() {
    }

    public Collection<D> toDtoCollection(Collection<T> staffs) {
        Collection<D> result = new ArrayList<>();
        for (T staff : staffs) {
            result.add(toDTO(staff));
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

    public GenericDAO<Post> getPostDAO() {
        return postDAO;
    }

    public void setPostDAO(GenericDAO<Post> postDAO) {
        this.postDAO = postDAO;
    }
}
