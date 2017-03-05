package ecm.util.db;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;

/**
 * @author dkarachurin
 */
public class DbUtils {
    /**
     * Evaluates given string path (splitting by dot) and returns the desired path
     *
     * @param root       Root to start with
     * @param pathString Result path
     * @return Path to desired property
     */
    public static Path getCriteriaPath(Root root, String pathString) {
        String[] fields = pathString.split("\\.");
        Path path = root.get(fields[0]);

        for (int i = 1; i < fields.length; i++) {
            path = path.get(fields[i]);
        }

        return path;
    }

    /**
     *
     * @param object
     * @param id
     * @param <T>
     * @return
     */
    public static <T> T checkNotFoundWithId(T object, int id) {
        if (object == null) {
            throw new NotFoundException("Not found entity with " + "id=" + id);
        }
        return object;
    }

    /**
     *
     * @param object
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    /**
     *
     * @param found
     * @param msg
     */
    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}
