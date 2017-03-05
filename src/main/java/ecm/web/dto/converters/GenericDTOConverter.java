package ecm.web.dto.converters;

import java.util.Collection;

/**
 * @author dkarachurin
 */
public interface GenericDTOConverter<T, D> {
    T fromDTO(D dto);

    D toDTO(T entity);

    Collection<D> toDtoCollection(Collection<T> entities);

    Collection<T> fromDtoCollection(Collection<D> dtoCollection);
}
