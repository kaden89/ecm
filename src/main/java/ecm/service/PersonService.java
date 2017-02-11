package ecm.service;

import ecm.dao.DocumentGenericDAO;
import ecm.model.*;
import ecm.util.exceptions.HasLinksException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
//Bug with rollback https://java.net/jira/browse/GLASSFISH-20699
@Transactional(dontRollbackOn=HasLinksException.class)
public class PersonService extends GenericServiceImpl<Person>  {

    @Inject
    DocumentGenericDAO<Outgoing> outgoingDAO;

    @Inject
    DocumentGenericDAO<Incoming> incomingDAO;

    @Inject
    DocumentGenericDAO<Task> taskDAO;

    public PersonService() {
    }

    @Override
    public void delete(int id) {
        checkAvailabilityOfDocumentsByAuthorId(id);
        super.delete(id);
    }

    private void checkAvailabilityOfDocumentsByAuthorId(int id){
        List<Document> all = new ArrayList<>();
        all.addAll(outgoingDAO.findAllWithPersonId(id));
        all.addAll(incomingDAO.findAllWithPersonId(id));
        all.addAll(taskDAO.findAllWithPersonId(id));

        boolean haveDocuments = all.size() != 0;
        StringBuilder builder = new StringBuilder("Cannot delete "+find(id).toString() + ". He has links to next documents:");
        for (Document document : all) {
            builder.append(System.lineSeparator());
            builder.append(document.toString());
        }
        if (haveDocuments) throw new HasLinksException(builder.toString());
    }

    @Override
    public List<Person> findAllSorted(String field, String direction) {
        if (field.equals("positionName")) field = "position.post";
        return super.findAllSorted(field, direction);
    }
}
