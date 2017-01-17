package ecm.dao;

import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public interface GenericDAO<T> {
    List<T> findAll();

    T find(int id);

    T save(Storable entity);

    T update(T entity);

    void delete(int id);

    void deleteAll();

    List<T> findAllByAuthorId(int id);
}
