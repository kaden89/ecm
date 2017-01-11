package model.documents_factory;

import model.Document;
import model.Incoming;

import java.time.LocalDate;

import static model.documents_factory.util.DocumentPopulator.*;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class IncomingFactory extends AbstractDocumentsFactory {
    @Override
    public Document createDocument() {
        Incoming incoming = new Incoming();
        populateBasicsOfDocument(incoming);
        incoming.setSender(getRandomPerson());
        incoming.setRecipient(getRandomPerson());
        incoming.setOutboundRegDate(getRandomDate(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)));
        return incoming;
    }
}
