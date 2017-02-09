package ecm.service;

import ecm.dao.GenericDAO;
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
@Transactional
public class PersonService extends GenericServiceImpl<Person>  {

    @Inject
    GenericDAO<Outgoing> outgoingDAO;

    @Inject
    GenericDAO<Incoming> incomingDAO;

    @Inject
    GenericDAO<Task> taskDAO;

    public PersonService() {
    }

    @Override
    public void delete(int id) {
        checkAvailabilityOfDocumentsByAuthorId(id);
        super.delete(id);
    }

    private void checkAvailabilityOfDocumentsByAuthorId(int id){
        List<Document> all = new ArrayList<>();
        all.addAll(outgoingDAO.findAllByAuthorId(id));
        all.addAll(incomingDAO.findAllByAuthorId(id));
        all.addAll(taskDAO.findAllByAuthorId(id));

        boolean haveDocuments = all.size() != 0;
        StringBuilder builder = new StringBuilder("Cannot delete "+find(id).toString() + ". He has next documents:");
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
