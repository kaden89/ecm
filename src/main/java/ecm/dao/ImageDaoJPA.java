package ecm.dao;

import ecm.model.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dkarachurin on 25.01.2017.
 */
public class ImageDaoJPA {
    @PersistenceContext(unitName="EcmPersistence")
    EntityManager entityManager;

    public Image find(int ownerId) {
        return (Image) entityManager.createQuery("SELECT i FROM Image i WHERE i.owner.id =" + ownerId).getSingleResult();
    }

    public Image save(Storable entity) {
        if (entity.getId() == null || find(entity.getId())==null)
            entityManager.persist(entity);
        else
            entityManager.merge(entity);
        return find(entity.getId());
    }

    public Image update(Image image) {
        return entityManager.merge(image);
    }


    public void delete(int id){
        Image entity = find(id);
        entityManager.remove(entity);
    }

    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Image e").executeUpdate();
    }
}
