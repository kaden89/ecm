package ecm.service;

import ecm.model.Outgoing;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

import javax.ejb.Stateless;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Stateless
public class OutgoingService extends AbstractGenericServiceImpl<Outgoing> {

    public OutgoingService() {
    }

    @Override
    public Page<Outgoing> findAllSortedAndPageable(Sort sort, RangeHeader range) {
        switch (sort.getField()) {
            case "authorName": {
                sort.setField("author.fullname");
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
