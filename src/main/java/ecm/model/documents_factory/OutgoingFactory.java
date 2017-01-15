package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Outgoing;
import ecm.util.exceptions.DocumentExistsException;

import static ecm.model.documents_factory.util.DocumentPopulator.getRandomPerson;
import static ecm.model.documents_factory.util.DocumentPopulator.getRandomString;
import static ecm.model.documents_factory.util.DocumentPopulator.populateBasicsOfDocument;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class OutgoingFactory extends AbstractDocumentsFactory {
    @Override
    public Document createDocument() throws DocumentExistsException {
        Outgoing outgoing = new Outgoing();
        populateBasicsOfDocument(outgoing);
        outgoing.setRecipient(getRandomPerson());
        outgoing.setDeliveryMethod(getRandomString(10));
        return outgoing;
    }
}
