package ecm.service;

import ecm.dao.DocumentGenericDAO;
import ecm.model.*;
import ecm.util.exceptions.HasLinksException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Слой сервиса реализующий доступ к DAO класса {@link Person}
 * @author dkarachurin
 */
@Stateless
//Bug with rollback https://java.net/jira/browse/GLASSFISH-20699
@Transactional(dontRollbackOn = HasLinksException.class)
public class PersonService extends AbstractGenericServiceImpl<Person> {

    @Inject
    private DocumentGenericDAO<Outgoing> outgoingDAO;

    @Inject
    private DocumentGenericDAO<Incoming> incomingDAO;

    @Inject
    private DocumentGenericDAO<Task> taskDAO;

    public PersonService() {
    }

    @Override
    public void delete(int id) {
        checkAvailabilityOfDocumentsByAuthorId(id);
        super.delete(id);
    }

    private void checkAvailabilityOfDocumentsByAuthorId(int id) {
        List<Document> all = new ArrayList<>();
        all.addAll(outgoingDAO.findAllWithPersonId(id));
        all.addAll(incomingDAO.findAllWithPersonId(id));
        all.addAll(taskDAO.findAllWithPersonId(id));

        boolean haveDocuments = all.size() != 0;
        String err = "Cannot delete " + find(id).toString() + ". He has links to " + all.size() + " documents.";

        if (haveDocuments) throw new HasLinksException(err);
    }
}
