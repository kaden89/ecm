package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.util.exceptions.DocumentExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Абстрактная фабрика
 * @see IncomingFactory
 * @see OutgoingFactory
 * @see TaskFactory
 * @author dkarachurin
 */
public abstract class AbstractDocumentsFactory {
    @Inject
    private DocumentPopulator populator;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Создает документ
     * @return Созданный документ
     * @throws DocumentExistsException
     */
    public abstract Document createDocument() throws DocumentExistsException;

    public DocumentPopulator getPopulator() {
        return populator;
    }

    public Logger getLog() {
        return log;
    }
}
