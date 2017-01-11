package model.documents_factory;

import model.Document;
import model.Outgoing;

import static model.documents_factory.util.DocumentPopulator.getRandomPerson;
import static model.documents_factory.util.DocumentPopulator.populateBasicsOfDocument;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class OutgoingFactory extends AbstractDocumentsFactory {
    @Override
    public Document createDocument() {
        Outgoing outgoing = new Outgoing();
        populateBasicsOfDocument(outgoing);
        outgoing.setRecipient(getRandomPerson());
        return outgoing;
    }
}
