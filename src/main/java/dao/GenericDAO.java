package dao;

import model.Person;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public interface GenericDAO<T> {
    List<T> findAll();

    T find(int id);

    void save(Storable entity);

    T update(T entity);

    void delete(int id);
}
