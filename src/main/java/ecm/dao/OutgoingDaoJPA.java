package ecm.dao;

import ecm.model.Incoming;
import ecm.model.Outgoing;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 16.01.2017.
 */
@Singleton
@Transactional
public class OutgoingDaoJPA extends GenericDaoJpa<Outgoing> {
}
