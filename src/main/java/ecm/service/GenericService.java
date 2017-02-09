package ecm.service;

import javax.transaction.Transactional;
import java.util.List;
import ecm.dao.GenericDAO;
/**
 * Created by dkarachurin on 08.02.2017.
 */
public interface GenericService<T> {

    @Transactional
    T find(int id);

    @Transactional
    List<T> findAll();

    @Transactional
    List<T> findAllSorted(String field, String direction);

    @Transactional
    T save(T newInstance);

    @Transactional
    T update(T updateInstance);

    @Transactional
    void delete(T entity);

    @Transactional
    void deleteAll();

    GenericDAO<T> getGenericDao();

    void setGenericDao(GenericDAO<T> genericDao);
}
