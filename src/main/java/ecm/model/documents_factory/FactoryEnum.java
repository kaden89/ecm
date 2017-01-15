package ecm.model.documents_factory;

import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Task;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public enum FactoryEnum {
    INCOMING(Incoming.class, IncomingFactory.class),
    OUTGOING(Outgoing.class, OutgoingFactory.class),
    TASK(Task.class, TaskFactory.class)
    ;

    private Class factoryClass;
    private Class docClass;

    FactoryEnum(Class docClass, Class factoryClass) {
        this.factoryClass = factoryClass;
        this.docClass = docClass;
    }

    public AbstractDocumentsFactory getFactory() throws IllegalAccessException, InstantiationException {
        return (AbstractDocumentsFactory) this.factoryClass.newInstance();
    }

}
