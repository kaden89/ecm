package ecm.dao;

import ecm.model.Task;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 16.01.2017.
 */
@Stateless
public class TaskDaoJPA extends DocumentGenericDaoJpa<Task> {
    @Override
    public List<Task> findAllWithPersonId(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        Predicate authorCondition = cb.equal(root.get("author").get("id"), id);
        Predicate executorCondition = cb.equal(root.get("executor").get("id"), id);
        Predicate controllerCondition = cb.equal(root.get("controller").get("id"), id);
        Predicate p = cb.or(authorCondition, executorCondition, controllerCondition);
        query.where(p);
        TypedQuery<Task> q = entityManager.createQuery(query);

        return  q.getResultList();
    }
}
