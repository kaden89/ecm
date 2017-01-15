package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.util.exceptions.DocumentExistsException;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public abstract class AbstractDocumentsFactory {
    public abstract Document createDocument() throws DocumentExistsException;

}
