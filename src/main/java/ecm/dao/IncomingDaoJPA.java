package ecm.dao;

import ecm.model.Incoming;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 16.01.2017.
 */
@Singleton
@Transactional
public class IncomingDaoJPA extends GenericDaoJpa<Incoming> {
}
