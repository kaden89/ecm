package ecm.dao;

import java.util.List;

/**
 * @author dkarachurin
 */
public interface DocumentGenericDAO<T> extends GenericDAO<T> {
    /**
     * Находит все документы в которых всречается {@link ecm.model.Person} с таким ID
     * @param id ID объекта {@link ecm.model.Person}
     * @return Список найденных документов
     */
    List<T> findAllWithPersonId(int id);
}
