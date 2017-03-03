package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Outgoing;
import ecm.util.exceptions.DocumentExistsException;


/**
 * Created by dkarachurin on 10.01.2017.
 */
public class OutgoingFactory extends AbstractDocumentsFactory {

    @Override
    public Document createDocument() throws DocumentExistsException {
        Outgoing outgoing = new Outgoing();
        getPopulator().populateBasicsOfDocument(outgoing);
        outgoing.setRecipient(getPopulator().getRandomPerson());
        outgoing.setDeliveryMethod(getPopulator().getRandomString(10));
        return outgoing;
    }
}
