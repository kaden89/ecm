package ecm.service;

import ecm.dao.GenericDAO;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

import java.util.List;

/**
 * Created by dkarachurin on 08.02.2017.
 */

public interface GenericService<T> {

    T find(int id);

    List<T> findAll();

    Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range);

    Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range);

    T save(T newInstance);

    T update(T updateInstance);

    void delete(int id);

    void deleteAll();

    GenericDAO<T> getGenericDao();

    void setGenericDao(GenericDAO<T> genericDao);
}
