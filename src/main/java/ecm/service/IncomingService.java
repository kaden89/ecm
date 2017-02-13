package ecm.service;

import ecm.model.Incoming;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class IncomingService extends AbstractGenericServiceImpl<Incoming> {

    public IncomingService() {
    }

    @Override
    public Page<Incoming> findAllSortedAndPageable(Sort sort, RangeHeader range) {
        switch (sort.getField()){
            case "authorName": {
                sort.setField("author.fullname");
                break;
            }
            case "senderName": {
                sort.setField("sender.fullname");
                break;
            }
            case "recipientName": {
                sort.setField("recipient.fullname");
                break;
            }
        }
        return super.findAllSortedAndPageable(sort, range);
    }
}
