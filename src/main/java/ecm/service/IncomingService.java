package ecm.service;

import ecm.dao.GenericDAO;
import ecm.model.Incoming;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class IncomingService extends GenericServiceImpl<Incoming> {

    public IncomingService() {
    }

    @Override
    public List<Incoming> findAllSorted(String field, String direction) {
        switch (field){
            case "authorName": {
                field = "author.firstname";
                break;
            }
            case "senderName": {
                field = "sender.firstname";
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
