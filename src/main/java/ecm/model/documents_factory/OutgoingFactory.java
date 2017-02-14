package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Outgoing;
import ecm.util.exceptions.DocumentExistsException;

import javax.inject.Inject;


/**
 * Created by dkarachurin on 10.01.2017.
 */
public class OutgoingFactory extends AbstractDocumentsFactory {

    @Override
    public Document createDocument() throws DocumentExistsException {
        Outgoing outgoing = new Outgoing();
        populator.populateBasicsOfDocument(outgoing);
        outgoing.setRecipient(populator.getRandomPerson());
        outgoing.setDeliveryMethod(populator.getRandomString(10));
        return outgoing;
    }
}
