package ecm;

import ecm.web.DocumentsRestController;
import ecm.web.EmployeeRestController;
import ecm.web.WidgetRestController;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@ApplicationPath("/rest")
public class AppConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(EmployeeRestController.class);
        classes.add(DocumentsRestController.class);
        classes.add(WidgetRestController.class);
        classes.add(MultiPartFeature.class);

        return classes;
    }
}
