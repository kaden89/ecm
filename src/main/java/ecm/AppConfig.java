package ecm;

import ecm.web.HelloController;
import ecm.web.controllers.EmployeeRestController;
import ecm.web.controllers.IncomingRestController;
import ecm.web.controllers.OutgoingRestController;
import ecm.web.controllers.TaskRestController;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Конфигурационный файл приложения
 * @author dkarachurin
 */
@ApplicationPath("/rest")
public class AppConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(EmployeeRestController.class);
        classes.add(IncomingRestController.class);
        classes.add(OutgoingRestController.class);
        classes.add(TaskRestController.class);
        classes.add(HelloController.class);
        classes.add(MultiPartFeature.class);

        return classes;
    }
}
