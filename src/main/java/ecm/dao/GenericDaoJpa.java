package ecm.dao;

import ecm.model.Person;
import ecm.util.filtering.Filter;
import ecm.util.filtering.Rule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dkarachurin on 13.01.2017.
 */
public abstract class GenericDaoJpa<T> implements GenericDAO<T> {

    @PersistenceContext(unitName="EcmPersistence")
    EntityManager entityManager;

    Class<T> entityClass;

    public GenericDaoJpa() {

        Class obtainedClass = getClass();
        Type genericSuperclass = null;
        for(;;) {
            genericSuperclass = obtainedClass.getGenericSuperclass();
            if(genericSuperclass instanceof ParameterizedType) {
                break;
            }
            obtainedClass = obtainedClass.getSuperclass();
        }
        ParameterizedType genericSuperclass_ = (ParameterizedType) genericSuperclass;
        this.entityClass = ((Class) ((Class) genericSuperclass_.getActualTypeArguments()[0]));
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM "+entityClass.getSimpleName()+" e").getResultList();
    }

    @Override
    public List<T> findAllByAuthorId(int id) {
        return entityManager.createQuery("SELECT e FROM "+entityClass.getSimpleName()+" e where e.author.id = "+id).getResultList();
    }

    @Override
    public T find(int id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }


    @Override
    public void delete(T entity){
        entityManager.remove(entity);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM "+entityClass.getSimpleName()+" e").executeUpdate();
    }

    @Override
    public List<T> findAllSorted(String sortField, String direction) {
        return entityManager.createQuery("SELECT e FROM "+entityClass.getSimpleName()+" e ORDER BY e."+ sortField +" "+direction).getResultList();
    }

    @Override
    public List<T> findAllSortedAndFiltered(String sortField, String direction, Filter filter) {
        Map<String, Object> params = filter.getQueryParams();

        Query query = entityManager.createQuery("SELECT e FROM "+entityClass.getSimpleName()+" e WHERE"+filter.toString()+"ORDER BY e."+ sortField +" "+direction);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
}
