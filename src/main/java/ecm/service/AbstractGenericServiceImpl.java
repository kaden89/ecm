package ecm.service;

import ecm.dao.GenericDAO;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dkarachurin on 08.02.2017.
 */
public abstract class AbstractGenericServiceImpl<T> implements GenericService<T> {

    @Inject
    private GenericDAO<T> genericDao;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AbstractGenericServiceImpl() {
    }

    @Override
    public T find(int id) {
        return this.getGenericDao().find(id);
    }

    @Override
    public List<T> findAll() {
        return this.getGenericDao().findAll();
    }

    @Override
    public T save(T newInstance) {
        return this.getGenericDao().save(newInstance);
    }

    @Override
    public T update(T updateInstance) {
        return this.getGenericDao().update(updateInstance);
    }

    @Override
    public void delete(int id) {
        this.getGenericDao().delete(getGenericDao().find(id));
    }

    @Override
    public GenericDAO<T> getGenericDao() {
        return genericDao;
    }

    @Override
    public void setGenericDao(GenericDAO<T> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range) {
        return genericDao.findAllSortedAndPageable(sort, range);
    }

    @Override
    public void deleteAll() {
        genericDao.deleteAll();
    }

    @Override
    public Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range) {
        return genericDao.findAllSortedFilteredAndPageable(sort, filter, range);
    }
}
