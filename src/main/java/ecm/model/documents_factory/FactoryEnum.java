package ecm.model.documents_factory;

import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * Created by dkarachurin on 10.01.2017.
 */

public enum FactoryEnum {
    INCOMING(Incoming.class, IncomingFactory.class),
    OUTGOING(Outgoing.class, OutgoingFactory.class),
    TASK(Task.class, TaskFactory.class);

    private Class factoryClass;
    private Class docClass;

    FactoryEnum(Class docClass, Class factoryClass) {
        this.factoryClass = factoryClass;
        this.docClass = docClass;
    }

    public AbstractDocumentsFactory getFactory() throws IllegalAccessException, InstantiationException {
        return (AbstractDocumentsFactory) CDI.current().select(factoryClass).get();
    }

}
