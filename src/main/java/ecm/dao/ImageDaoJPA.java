package ecm.dao;

import ecm.model.Image;

import javax.ejb.Stateless;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by dkarachurin on 25.01.2017.
 */
@Stateless
public class ImageDaoJPA extends AbstractImageDAO {

    public Image findByOwnerId(int ownerId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Image> query = cb.createQuery(Image.class);
        Root<Image> root = query.from(Image.class);
        Predicate condition = cb.equal(root.get("ownerId"), ownerId);
        query.where(condition);
        TypedQuery<Image> q = getEntityManager().createQuery(query);
        List<Image> results = q.getResultList();

        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        }
        throw new NonUniqueResultException();
    }

    public Image save(Image image) {
        getEntityManager().persist(image);
        return image;
    }

    public Image update(Image image) {
        return getEntityManager().merge(image);
    }

    public void deleteByOwnerId(int id) {
        Image entity = findByOwnerId(id);
        getEntityManager().remove(entity);
    }

    public void deleteAll() {
        getEntityManager().createQuery("DELETE FROM Image i").executeUpdate();
    }
}
