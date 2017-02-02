package ecm.web.dto;

import java.util.Collection;

/**
 * Created by dkarachurin on 02.02.2017.
 */
public interface DTOConverter<E,D> {
    E fromDTO(D dto);
    D toDTO(E entity);
    Collection<D> toDtoCollection(Collection<E> entities);
    Collection<E> fromDtoCollection(Collection<D> dtoCollection);
}
