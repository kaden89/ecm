package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.util.exceptions.DocumentExistsException;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class DocumentsFactory {
    private IncomingFactory incomingFactory;
    private OutgoingFactory outgoingFactory;
    private TaskFactory taskFactory;
    public static final DocumentsFactory INSTANCE = new DocumentsFactory();

    private DocumentsFactory() {
        incomingFactory = new IncomingFactory();
        outgoingFactory = new OutgoingFactory();
        taskFactory = new TaskFactory();
    }

    public Document createDocument(FactoryEnum factoryEnum) throws InstantiationException, IllegalAccessException, DocumentExistsException {
        return factoryEnum.getFactory().createDocument();
    }
}
