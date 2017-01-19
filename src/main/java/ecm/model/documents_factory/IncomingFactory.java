package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Incoming;
import ecm.model.documents_factory.util.DocumentPopulator;
import ecm.util.exceptions.DocumentExistsException;

import javax.inject.Inject;
import java.time.LocalDate;

import static ecm.model.documents_factory.util.DocumentPopulator.*;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class IncomingFactory extends AbstractDocumentsFactory {
    @Inject
    DocumentPopulator populator;

    @Override
    public Document createDocument() throws DocumentExistsException {
        Incoming incoming = new Incoming();
        populator.populateBasicsOfDocument(incoming);
        incoming.setSender(populator.getRandomPerson());
        incoming.setRecipient(populator.getRandomPerson());
        incoming.setOutboundRegDate(populator.getRandomDate(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)));
        return incoming;
    }
}