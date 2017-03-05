package ecm.dao;

import ecm.model.Task;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * JPA DAO для документа {@link Task}
 * @author dkarachurin
 */
@Stateless
public class TaskDaoJPA extends DocumentGenericDaoJpa<Task> {
    @Override
    public List<Task> findAllWithPersonId(int id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        Predicate authorCondition = cb.equal(root.get("author").get("id"), id);
        Predicate executorCondition = cb.equal(root.get("executor").get("id"), id);
        Predicate controllerCondition = cb.equal(root.get("controller").get("id"), id);
        Predicate p = cb.or(authorCondition, executorCondition, controllerCondition);
        query.where(p);
        TypedQuery<Task> q = getEntityManager().createQuery(query);

        return q.getResultList();
    }
}
