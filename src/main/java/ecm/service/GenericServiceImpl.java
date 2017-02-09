package ecm.service;

import ecm.dao.GenericDAO;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dkarachurin on 08.02.2017.
 */
public abstract class GenericServiceImpl<T> implements GenericService<T> {

    @Inject
    private GenericDAO<T> genericDao;

    public GenericServiceImpl() {
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
    public void delete(T entity) {
        this.getGenericDao().delete(entity);
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
    public List<T> findAllSorted(String field, String direction) {
        return genericDao.findAllSorted(field, direction);
    }

    @Override
    public void deleteAll() {

    }
}
