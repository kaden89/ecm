package ecm.dao;

import ecm.model.Outgoing;

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
public class OutgoingDaoJPA extends DocumentGenericDaoJpa<Outgoing> {
    @Override
    public List<Outgoing> findAllWithPersonId(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Outgoing> query = cb.createQuery(Outgoing.class);
        Root<Outgoing> root = query.from(Outgoing.class);
        Predicate authorCondition = cb.equal(root.get("author").get("id"), id);
        Predicate recipientCondition = cb.equal(root.get("recipient").get("id"), id);
        Predicate p = cb.or(authorCondition, recipientCondition);
        query.where(p);
        TypedQuery<Outgoing> q = entityManager.createQuery(query);

        return  q.getResultList();
    }
}
