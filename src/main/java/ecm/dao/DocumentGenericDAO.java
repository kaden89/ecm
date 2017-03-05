package ecm.dao;

import java.util.List;

/**
 * @author dkarachurin
 */
public interface DocumentGenericDAO<T> extends GenericDAO<T> {
    /**
     *
     * @param id Находит все документы в котоых всречается {@link ecm.model.Person} с таким ID
     * @return Список найденных документов
     */
    List<T> findAllWithPersonId(int id);
}
