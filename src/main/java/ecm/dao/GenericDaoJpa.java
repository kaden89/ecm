package ecm.dao;

import ecm.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

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
    public List<T> findAllSorted(String fieldName, String direction) {
        return entityManager.createQuery("SELECT e FROM "+entityClass.getSimpleName()+" e ORDER BY e."+fieldName+" "+direction).getResultList();
    }
}
