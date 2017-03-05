package ecm.web.dto.converters;

import java.util.Collection;

/**
 * Интерфейс generic конвертера ДТО-Объект
 * @author dkarachurin
 */
public interface GenericDTOConverter<T, D> {
    /**
     * Создает объект из ДТО
     * @param dto ДТО
     * @return Объект
     */
    T fromDTO(D dto);

    /**
     * Создает ДТО из объекта
     * @param entity Объект
     * @return ДТО
     */
    D toDTO(T entity);

    /**
     * Конвертирует коллекцию объектов в коллекцию ДТО
     * @param entities Коллекция объектов
     * @return коллекция ДТО
     */
    Collection<D> toDtoCollection(Collection<T> entities);

    /**
     * Конвертирует коллекцию ДТО в коллекцию объектов
     * @param dtoCollection Коллекция ДТО
     * @return Коллекция объектов
     */
    Collection<T> fromDtoCollection(Collection<D> dtoCollection);
}
