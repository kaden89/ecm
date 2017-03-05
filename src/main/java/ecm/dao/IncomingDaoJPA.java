package ecm.dao;

import ecm.model.Incoming;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 *  JPA DAO для документа {@link Incoming}
 * @author dkarachurin
 */
@Stateless
public class IncomingDaoJPA extends DocumentGenericDaoJpa<Incoming> {
    @Override
    public List<Incoming> findAllWithPersonId(int id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Incoming> query = cb.createQuery(Incoming.class);
        Root<Incoming> root = query.from(Incoming.class);
        Predicate authorCondition = cb.equal(root.get("author").get("id"), id);
        Predicate senderCondition = cb.equal(root.get("sender").get("id"), id);
        Predicate recipientCondition = cb.equal(root.get("recipient").get("id"), id);
        Predicate p = cb.or(authorCondition, senderCondition, recipientCondition);
        query.where(p);
        TypedQuery<Incoming> q = getEntityManager().createQuery(query);

        return q.getResultList();
    }
}
