package ecm.service;

import ecm.dao.DocumentGenericDAO;
import ecm.model.*;
import ecm.util.exceptions.HasLinksException;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

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
@Transactional(dontRollbackOn = HasLinksException.class)
public class PersonService extends AbstractGenericServiceImpl<Person> {

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

    private void checkAvailabilityOfDocumentsByAuthorId(int id) {
        List<Document> all = new ArrayList<>();
        all.addAll(outgoingDAO.findAllWithPersonId(id));
        all.addAll(incomingDAO.findAllWithPersonId(id));
        all.addAll(taskDAO.findAllWithPersonId(id));

        boolean haveDocuments = all.size() != 0;
        String err = "Cannot delete " + find(id).toString() + ". He has links to "+all.size()+" documents.";

        if (haveDocuments) throw new HasLinksException(err);
    }

    @Override
    public Page<Person> findAllSortedAndPageable(Sort sort, RangeHeader range) {
        if (sort.getField().equals("positionName")) sort.setField("position.post");
        return super.findAllSortedAndPageable(sort, range);
    }
}
