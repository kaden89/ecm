package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.util.exceptions.DocumentExistsException;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public abstract class AbstractDocumentsFactory {
    @Inject
    DocumentPopulator populator;
    
    @Inject
    private transient Logger log;

    public abstract Document createDocument() throws DocumentExistsException;
}
