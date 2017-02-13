package ecm.model.documents_factory;

import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Task;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Logger;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public enum FactoryEnum {
    INCOMING(Incoming.class, IncomingFactory.class),
    OUTGOING(Outgoing.class, OutgoingFactory.class),
    TASK(Task.class, TaskFactory.class);

    private Class factoryClass;
    private Class docClass;
    protected final Logger log = Logger.getLogger(getClass().getName());

    FactoryEnum(Class docClass, Class factoryClass) {
        this.factoryClass = factoryClass;
        this.docClass = docClass;
    }

    public AbstractDocumentsFactory getFactory() throws IllegalAccessException, InstantiationException {
        AbstractDocumentsFactory factory = null;
        try {
            InitialContext ic = new InitialContext();
            BeanManager bm = (BeanManager) ic.lookup("java:comp/BeanManager");
            Bean<AbstractDocumentsFactory> bean = (Bean<AbstractDocumentsFactory>) bm.getBeans(factoryClass).iterator().next();
            CreationalContext<AbstractDocumentsFactory> ctx = bm.createCreationalContext(bean);
            factory = (AbstractDocumentsFactory) bm.getReference(bean, factoryClass, ctx);

        } catch (NamingException e) {
            log.warning(e.getMessage());
        }
        return factory;
    }

}
