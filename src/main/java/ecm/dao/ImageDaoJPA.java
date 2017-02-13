package ecm.dao;

import ecm.model.Image;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 25.01.2017.
 */
@Singleton
@Transactional
public class ImageDaoJPA implements ImageDAO {
    @PersistenceContext(unitName = "EcmPersistence")
    EntityManager entityManager;

    public Image findByOwnerId(int ownerId) {
        try {
            return entityManager.createQuery("SELECT i FROM Image i WHERE i.ownerId =" + ownerId, Image.class).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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
