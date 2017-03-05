package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.util.exceptions.DocumentExistsException;

import javax.ejb.Singleton;

/**
 * @author dkarachurin
 */
@Singleton
public class DocumentsFactory {

    public DocumentsFactory() {
    }

    public Document createDocument(FactoryEnum factoryEnum) throws InstantiationException, IllegalAccessException, DocumentExistsException {
        return factoryEnum.getFactory().createDocument();
    }
}
