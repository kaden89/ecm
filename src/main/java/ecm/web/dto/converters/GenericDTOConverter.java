package ecm.web.dto.converters;

import java.util.Collection;

/**
 * Created by dkarachurin on 02.02.2017.
 */
public interface GenericDTOConverter<T, D> {
    T fromDTO(D dto);

    D toDTO(T entity);

    Collection<D> toDtoCollection(Collection<T> entities);

    Collection<T> fromDtoCollection(Collection<D> dtoCollection);
}
