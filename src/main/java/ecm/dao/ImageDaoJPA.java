package ecm.dao;

import ecm.model.Image;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 25.01.2017.
 */
@Stateless
public class ImageDaoJPA implements ImageDAO {
    @PersistenceContext(unitName = "EcmPersistence")
    EntityManager entityManager;

    public Image findByOwnerId(int ownerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Image> query = cb.createQuery(Image.class);
        Root<Image> root = query.from(Image.class);
        Predicate condition = cb.equal(root.get("ownerId"), ownerId);
        query.where(condition);
        TypedQuery<Image> q = entityManager.createQuery(query);
        List<Image> results = q.getResultList();

        if (results.isEmpty()){
            return null;
        }
        else if (results.size() == 1){
            return results.get(0);
        }
        throw new NonUniqueResultException();
    }

    public Image save(Image image) {
        entityManager.persist(image);
        return image;
    }

    public Image update(Image image) {
        return entityManager.merge(image);
    }

    public Image saveOrUpdate(Image image) {
        return entityManager.merge(image);
    }


    public void deleteByOwnerId(int id) {
        Image entity = findByOwnerId(id);
        entityManager.remove(entity);
    }

    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Image i").executeUpdate();
    }
}
