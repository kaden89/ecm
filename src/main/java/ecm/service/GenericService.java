package ecm.service;

import javax.transaction.Transactional;
import java.util.List;
import ecm.dao.GenericDAO;
import ecm.util.filtering.Filter;

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

    List<T> findAllSortedAndFiltered(String field, String direction, Filter filter);

    @Transactional
    T save(T newInstance);

    @Transactional
    T update(T updateInstance);

    @Transactional
    void delete(int id);

    @Transactional
    void deleteAll();

    GenericDAO<T> getGenericDao();

    void setGenericDao(GenericDAO<T> genericDao);
}
