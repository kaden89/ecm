package ecm.dao;

import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Direction;
import ecm.util.sorting.Sort;
import org.hibernate.query.internal.QueryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by dkarachurin on 13.01.2017.
 */
public abstract class AbstractGenericDaoJpaImpl<T> implements GenericDAO<T> {

    @PersistenceContext(unitName = "EcmPersistence")
    EntityManager entityManager;

    Class<T> entityClass;

    @Inject
    private transient Logger log;

    public AbstractGenericDaoJpaImpl() {

        Class obtainedClass = getClass();
        Type genericSuperclass;
        for (; ; ) {
            genericSuperclass = obtainedClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                break;
            }
            obtainedClass = obtainedClass.getSuperclass();
        }
        ParameterizedType genericSuperclass_ = (ParameterizedType) genericSuperclass;
        this.entityClass = ((Class) (genericSuperclass_.getActualTypeArguments()[0]));
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        TypedQuery<T> q = entityManager.createQuery(query);

        return q.getResultList();
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
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM " + entityClass.getSimpleName() + " e").executeUpdate();
    }

    @Override
    public Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select((Selection<? extends T>) cb.count(root));
        Long countResult = (Long) entityManager.createQuery(query).getSingleResult();
        query.select(root);
        Path path = getCriteriaPath(root, sort.getField());
        query.orderBy(sort.getDirection().equals(Direction.ASC) ? cb.asc(path) : cb.desc(path));
        TypedQuery<T> resultQuery = entityManager.createQuery(query);
        resultQuery.setFirstResult(range.getOffset());
        resultQuery.setMaxResults(range.getLimit());

        return new Page(resultQuery.getResultList(), range.getOffset(), range.getOffset() + range.getLimit(), countResult != null ? countResult.intValue() : 0);
    }

    @Override
    public Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.where(filter.getFilterPredicate(cb,root, entityClass));
        Path path = getCriteriaPath(root, sort.getField());
        query.orderBy(sort.getDirection().equals(Direction.ASC) ? cb.asc(path) : cb.desc(path));
        query.select(root);
        TypedQuery<T> resultQuery = entityManager.createQuery(query);
        resultQuery.setFirstResult(range.getOffset());
        resultQuery.setMaxResults(range.getLimit());

        List<T> result = resultQuery.getResultList();

        CriteriaQuery<T> countQuery = cb.createQuery(entityClass);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.where(filter.getFilterPredicate(cb,countRoot, entityClass));
        countQuery.select((Selection<? extends T>) cb.count(countRoot));
        Long countResult = (Long) entityManager.createQuery(countQuery).getSingleResult();

        return new Page(result, range.getOffset(), range.getOffset() + range.getLimit(), countResult != null ? countResult.intValue() : 0);
    }

    /**
     * Evaluates given string path (splitting by dot) and returns the desired path
     *
     * @param root       Root to start with
     * @param pathString Result path
     * @return Path to desired property
     */
    private Path getCriteriaPath(Root root, String pathString) {
        String[] fields = pathString.split("\\.");
        Path path = root.get(fields[0]);

        for (int i = 1; i < fields.length; i++) {
            path = path.get(fields[i]);
        }

        return path;
    }
}
