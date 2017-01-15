package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Incoming;
import ecm.util.exceptions.DocumentExistsException;

import java.time.LocalDate;

import static ecm.model.documents_factory.util.DocumentPopulator.*;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class IncomingFactory extends AbstractDocumentsFactory {
    @Override
    public Document createDocument() throws DocumentExistsException {
        Incoming incoming = new Incoming();
        populateBasicsOfDocument(incoming);
        incoming.setSender(getRandomPerson());
        incoming.setRecipient(getRandomPerson());
        incoming.setOutboundRegDate(getRandomDate(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)));
        return incoming;
    }
}
