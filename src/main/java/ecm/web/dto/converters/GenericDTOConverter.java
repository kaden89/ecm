package ecm.web.dto.converters;

import java.util.Collection;

/**
 * Created by dkarachurin on 02.02.2017.
 */
public interface GenericDTOConverter<E,D> {
    E fromDTO(D dto);
    D toDTO(E entity);
    Collection<D> toDtoCollection(Collection<E> entities);
    Collection<E> fromDtoCollection(Collection<D> dtoCollection);
}
