package ecm.service;

import ecm.dao.GenericDAO;
import ecm.model.Outgoing;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class OutgoingService extends GenericServiceImpl<Outgoing> {

    public OutgoingService() {
    }
}
