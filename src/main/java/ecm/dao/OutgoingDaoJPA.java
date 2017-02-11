package ecm.dao;

import ecm.model.Incoming;
import ecm.model.Outgoing;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 16.01.2017.
 */
@Singleton
@Transactional
public class OutgoingDaoJPA extends DocumentGenericDaoJpa<Outgoing> {
    @Override
    public List<Outgoing> findAllWithPersonId(int id) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e where e.author.id = " + id +" OR e.recipient.id = "+ id).getResultList();
    }
}
