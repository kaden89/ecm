package ecm.dao;

import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

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

    Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range);

    Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range);
}
