package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.util.exceptions.DocumentExistsException;

import javax.ejb.Singleton;

/**
 * Фабрика документов
 * @author dkarachurin
 */
@Singleton
public class DocumentsFactory {

    public DocumentsFactory() {
    }

    /**
     *
     * @param factoryEnum Элемент {@link FactoryEnum} для определения вида нужного документа
     * @return Документ нужного класса
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws DocumentExistsException
     */
    public Document createDocument(FactoryEnum factoryEnum) throws InstantiationException, IllegalAccessException, DocumentExistsException {
        return factoryEnum.getFactory().createDocument();
    }
}
