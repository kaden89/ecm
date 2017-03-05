package ecm.dao;

import ecm.util.db.DbUtils;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Direction;
import ecm.util.sorting.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Абстарктная реализация {@link GenericDAO}
 * @see PersonDaoJPA
 * @see PostDaoJPA
 * @author dkarachurin
 */
public abstract class AbstractGenericDaoJpaImpl<T> implements GenericDAO<T> {

    @PersistenceContext(unitName = "EcmPersistence")
    private EntityManager entityManager;

    private Class<T> entityClass;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AbstractGenericDaoJpaImpl() {
    }

    /**
     * Определение класса для которого создано DAO
     */
    @PostConstruct
    private void resolveEntityClass() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> delete = cb.createCriteriaDelete(entityClass);
        Root<T> e = delete.from(entityClass);
        entityManager.createQuery(delete).executeUpdate();
    }

    @Override
    public Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select((Selection<? extends T>) cb.count(root));
        Long countResult = (Long) entityManager.createQuery(query).getSingleResult();
        query.select(root);
        Path path = DbUtils.getCriteriaPath(root, sort.getField());
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
        query.where(filter.getFilterPredicate(cb, root, entityClass));
        Path path = DbUtils.getCriteriaPath(root, sort.getField());
        query.orderBy(sort.getDirection().equals(Direction.ASC) ? cb.asc(path) : cb.desc(path));
        query.select(root);
        TypedQuery<T> resultQuery = entityManager.createQuery(query);
        resultQuery.setFirstResult(range.getOffset());
        resultQuery.setMaxResults(range.getLimit());

        List<T> result = resultQuery.getResultList();

        CriteriaQuery<T> countQuery = cb.createQuery(entityClass);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.where(filter.getFilterPredicate(cb, countRoot, entityClass));
        countQuery.select((Selection<? extends T>) cb.count(countRoot));
        Long countResult = (Long) entityManager.createQuery(countQuery).getSingleResult();

        return new Page(result, range.getOffset(), range.getOffset() + range.getLimit(), countResult != null ? countResult.intValue() : 0);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Logger getLog() {
        return log;
    }
}
