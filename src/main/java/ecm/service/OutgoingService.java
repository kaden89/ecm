package ecm.service;

import ecm.dao.GenericDAO;
import ecm.model.Outgoing;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class OutgoingService extends GenericServiceImpl<Outgoing> {

    public OutgoingService() {
    }

    @Override
    public List<Outgoing> findAllSorted(String field, String direction) {
        switch (field){
            case "authorName": {
                field = "author.firstname";
                break;
            }
            case "recipientName": {
                field = "recipient.firstname";
                break;
            }
        }
        return super.findAllSorted(field, direction);
    }
}
