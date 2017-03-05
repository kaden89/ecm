package ecm.dao;

import ecm.model.Avatar;

import javax.ejb.Stateless;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * JPA Реализация интерфейса {@link AvatarDAO}
 * @author dkarachurin
 */
@Stateless
public class AvatarDaoJPA extends AbstractAvatarDAO {

    public Avatar findByOwnerId(int ownerId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Avatar> query = cb.createQuery(Avatar.class);
        Root<Avatar> root = query.from(Avatar.class);
        Predicate condition = cb.equal(root.get("ownerId"), ownerId);
        query.where(condition);
        TypedQuery<Avatar> q = getEntityManager().createQuery(query);
        List<Avatar> results = q.getResultList();

        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        }
        throw new NonUniqueResultException();
    }

    public Avatar save(Avatar avatar) {
        getEntityManager().persist(avatar);
        return avatar;
    }

    public Avatar update(Avatar avatar) {
        return getEntityManager().merge(avatar);
    }

    public void delete(Avatar avatar) {
        getEntityManager().remove(avatar);
    }

    public void deleteAll() {
        getEntityManager().createQuery("DELETE FROM Avatar i").executeUpdate();
    }
}
