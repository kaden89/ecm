package ecm.dao;

import ecm.model.*;
import ecm.util.exceptions.HasLinksException;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Singleton
@Transactional
public class PersonDaoJPA extends GenericDaoJpa<Person>{
    @Inject
    GenericDAO<Outgoing> outgoingDAO;

    @Inject
    GenericDAO<Incoming> incomingDAO;

    @Inject
    GenericDAO<Task> taskDAO;

    @Override
    public void delete(int id) {
        checkAvailabilityOfDocumentsByAuthorId(id);
        super.delete(id);
    }

    private void checkAvailabilityOfDocumentsByAuthorId(int id){
        List<Outgoing> outgoings = outgoingDAO.findAllByAuthorId(id);
        List<Incoming> incomings = incomingDAO.findAllByAuthorId(id);
        List<Task> tasks = taskDAO.findAllByAuthorId(id);
        boolean haveDocuments = outgoings.size() != 0 || incomings.size() != 0 || tasks.size() != 0;
        if (haveDocuments) throw new HasLinksException("Cannot delete Person with id = "+id + ".He has some documents!");
    }
}
