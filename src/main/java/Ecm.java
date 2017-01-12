import web.EmployeeRestController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@ApplicationPath("/rest")
public class Ecm extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(EmployeeRestController.class);
        return classes;
    }
}
