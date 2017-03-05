package ecm.model.documents_factory;

import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Task;

import javax.enterprise.inject.spi.CDI;

/**
 * Перечисление типов фабрик
 * @author dkarachurin
 */

public enum FactoryEnum {
    INCOMING(IncomingFactory.class),
    OUTGOING(OutgoingFactory.class),
    TASK(TaskFactory.class);

    private Class factoryClass;

    FactoryEnum(Class factoryClass) {
        this.factoryClass = factoryClass;
    }

    /**
     * Возвращает инстанс фабрики указанной в параметре перечисления
     * @return Фабрика для документа
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public AbstractDocumentsFactory getFactory() throws IllegalAccessException, InstantiationException {
        return (AbstractDocumentsFactory) CDI.current().select(factoryClass).get();
    }

}
