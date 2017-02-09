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

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteAll();

    List<T> findAllByAuthorId(int id);

    List<T> findAllSorted(String fieldName, String direction);
}
