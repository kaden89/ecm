package ecm.service;

import ecm.dao.GenericDAO;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * @author dkarachurin
 */

public interface GenericService<T> {

    T find(int id) throws NotFoundException;

    List<T> findAll();

    Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range) ;

    Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range);

    T save(T newInstance);

    T update(T updateInstance);

    void delete(int id) throws NotFoundException;

    void deleteAll();

    GenericDAO<T> getGenericDao();
}
