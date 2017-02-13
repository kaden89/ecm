package ecm.service;

import javax.transaction.Transactional;
import java.util.List;
import ecm.dao.GenericDAO;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

/**
 * Created by dkarachurin on 08.02.2017.
 */
public interface GenericService<T> {

    @Transactional
    T find(int id);

    @Transactional
    List<T> findAll();

    @Transactional
    Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range);

    Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range);

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
