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
import java.util.ArrayList;
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
        List<Document> all = new ArrayList<>();
        all.addAll(outgoingDAO.findAllByAuthorId(id));
        all.addAll(incomingDAO.findAllByAuthorId(id));
        all.addAll(taskDAO.findAllByAuthorId(id));

        boolean haveDocuments = all.size() != 0;
        StringBuilder builder = new StringBuilder("Cannot delete "+find(id).toString() + ". He has next documents:");
        for (Document document : all) {
            builder.append(System.lineSeparator());
            builder.append(document.getName());
        }
        if (haveDocuments) throw new HasLinksException(builder.toString());
    }
}
