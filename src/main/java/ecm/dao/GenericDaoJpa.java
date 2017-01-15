package ecm.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dkarachurin on 13.01.2017.
 */
public abstract class GenericDaoJpa<T> implements GenericDAO<T> {

    @PersistenceContext(unitName="EcmPersistence")
    private EntityManager entityManager;

    private Class<T> entityClass;

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
    public T find(int id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public T save(Storable entity) {
        if (find(entity.getId()) == null)
            entityManager.persist(entity);
        else
            entityManager.merge(entity);
        return find(entity.getId());
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }


    @Override
    public void delete(int id) {
        entityManager.remove(find(id));
    }
}
