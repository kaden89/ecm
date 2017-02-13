package ecm.dao;

import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;
import org.hibernate.query.internal.QueryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e").getResultList();
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
        Query queryTotal = entityManager.createQuery("SELECT COUNT (e.id) FROM " + entityClass.getSimpleName() + " e");
        Long countResult = (Long) queryTotal.getSingleResult();
        Query query = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e ORDER BY e." + sort.getField() + " " + sort.getDirection());
        query.setFirstResult(range.getOffset());
        query.setMaxResults(range.getLimit());
        return new Page(query.getResultList(), range.getOffset(), range.getOffset() + range.getLimit(), countResult != null ? countResult.intValue() : 0);
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
}
