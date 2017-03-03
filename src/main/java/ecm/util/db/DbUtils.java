package ecm.util.db;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * @author dkarachurin on 03.03.2017.
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
}
