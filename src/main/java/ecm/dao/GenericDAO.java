package ecm.dao;

import javax.transaction.TransactionalException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public interface GenericDAO<T> {
    List<T> findAll();

    T find(int id);

    T save(Storable entity);

    T update(T entity);

    void delete(int id) throws TransactionalException;

    void deleteAll();

    List<T> findAllByAuthorId(int id);

    List<T> findAllSortable(String fieldName, boolean desc);
}
