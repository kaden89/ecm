package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Incoming;
import ecm.util.exceptions.DocumentExistsException;

import java.time.LocalDate;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class IncomingFactory extends AbstractDocumentsFactory {

    @Override
    public Document createDocument() throws DocumentExistsException {
        Incoming incoming = new Incoming();
        getPopulator().populateBasicsOfDocument(incoming);
        incoming.setSender(getPopulator().getRandomPerson());
        incoming.setRecipient(getPopulator().getRandomPerson());
        incoming.setOutboundRegDate(getPopulator().getRandomDate(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 31)));
        incoming.setReferenceNumber(String.valueOf(getPopulator().getRandomInt(1000, 4000)) + getPopulator().getRandomString(4));
        return incoming;
    }
}
