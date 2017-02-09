package ecm.service;

import ecm.dao.GenericDAO;
import ecm.model.Incoming;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class IncomingService extends GenericServiceImpl<Incoming> {

    public IncomingService() {
    }

}
