package ecm.dao;

import ecm.model.Incoming;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 16.01.2017.
 */
@Singleton
@Transactional
public class IncomingDaoJPA extends DocumentGenericDaoJpa<Incoming> {
    @Override
    public List<Incoming> findAllWithPersonId(int id) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e where e.author.id = " + id
                + " OR e.sender.id = " + id + " OR e.recipient.id = " + id).getResultList();
    }
}
