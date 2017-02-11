package ecm.dao;

import ecm.util.filtering.Filter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by dkarachurin on 13.01.2017.
 */
public abstract class GenericDaoJpa<T> implements GenericDAO<T> {

    @PersistenceContext(unitName = "EcmPersistence")
    EntityManager entityManager;

    Class<T> entityClass;

    public GenericDaoJpa() {

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
    public List<T> findAllByAuthorId(int id) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e where e.author.id = " + id).getResultList();
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
    public List<T> findAllSorted(String sortField, String direction) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e ORDER BY e." + sortField + " " + direction).getResultList();
    }

    @Override
    public List<T> findAllSortedAndFiltered(String sortField, String direction, Filter filter) {
        Map<String, Object> params = filter.getQueryParams();
        StringBuilder builder = new StringBuilder();
        Query query = entityManager.createQuery(builder.append("SELECT e FROM ")
                .append(entityClass.getSimpleName())
                .append(" e WHERE")
                .append(filter.getCaseInsensitiveQueryString(entityClass))
                .append("ORDER BY e.")
                .append(sortField)
                .append(" ")
                .append(direction).toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey().replaceAll("\\.", ""), getClassCastedParam(entry.getKey(), entry.getValue()));
        }
        return query.getResultList();
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
            e.printStackTrace();
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
