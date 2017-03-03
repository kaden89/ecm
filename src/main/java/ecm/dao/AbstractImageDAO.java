package ecm.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author dkarachurin on 03.03.2017.
 */
public abstract class AbstractImageDAO implements ImageDAO {
    @PersistenceContext(unitName = "EcmPersistence")
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
