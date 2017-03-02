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
        Map<String, Object> params = filter.getQueryParams();
        StringBuilder builder = new StringBuilder();
        Query query = entityManager.createQuery(builder.append("SELECT e FROM ")
                .append(entityClass.getSimpleName())
                .append(" e WHERE")
                .append(filter.getCaseInsensitiveQueryString(entityClass))
                .append("ORDER BY e.")
                .append(sort.getField())
                .append(" ")
                .append(sort.getDirection()).toString());
        query.setFirstResult(range.getOffset());
        query.setMaxResults(range.getLimit());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey().replaceAll("\\.", ""), getClassCastedParam(entry.getKey(), entry.getValue()));
        }
        //Count all possible items with current filter for Range header response
        String countQueryString = ((QueryImpl) query).getQueryString();
        countQueryString = countQueryString.replace("SELECT e FROM", "SELECT COUNT (e) FROM");
        countQueryString = countQueryString.replace("ORDER BY e." + sort.getField() + " " + sort.getDirection(), "");
        Query countQuery = entityManager.createQuery(countQueryString);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey().replaceAll("\\.", ""), getClassCastedParam(entry.getKey(), entry.getValue()));
        }
        Long countResult = (Long) countQuery.getSingleResult();
        return new Page(query.getResultList(), range.getOffset(), range.getOffset() + range.getLimit(), countResult != null ? countResult.intValue() : 0);
    }

    private Object getClassCastedParam(String paramName, Object param) {

        Object result = null;
        try {
            //Check superclass fields
            result = castParamToFieldClassType(Class.forName(entityClass.getCanonicalName()).getSuperclass(), paramName, param);
            if (result == null) {
                //Check our class fields
                result = castParamToFieldClassType(Class.forName(entityClass.getCanonicalName()), paramName, param);
            }

        } catch (ClassNotFoundException e) {
            log.warning(e.getMessage());
        }
        return result;
    }

    private Object castParamToFieldClassType(Class clazz, String paramName, Object param) {
        if (paramName.contains(".")) {
            try {
                Class childClass = clazz.getDeclaredField(paramName.split("\\.")[0]).getType();
                return castParamToFieldClassType(childClass, paramName.split("\\.")[1], param);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(paramName)) {
                Class paramClass = field.getType();
                if (paramClass == String.class) {
                    return String.valueOf(param);
                } else if (paramClass == Integer.class) {
                    return Integer.parseInt(String.valueOf(param));
                } else if (paramClass == LocalDate.class) {
                    return LocalDate.parse(String.valueOf(param));
                } else if (paramClass == boolean.class) {
                    return Boolean.valueOf(String.valueOf(param));
                }
            }
        }
        return null;
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
