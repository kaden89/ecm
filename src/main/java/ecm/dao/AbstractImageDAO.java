package ecm.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author dkarachurin
 */
public abstract class AbstractImageDAO implements ImageDAO {
    @PersistenceContext(unitName = "EcmPersistence")
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
