package ecm.util.db;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;

/**
 * Класс содержит утильные методы для работы с БД
 * @author dkarachurin
 */
public class DbUtils {
    /**
     * Вычисляет путь к полю по переданной строке для Criteria API
     *
     * @param root       Корневой путь
     * @param pathString Путь к полю в виде строки
     * @return Путь к полю в виде {@link Path}
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
     * Выбрасывает {@link NotFoundException} если объект null
     * @param object Искомый объект
     * @param id ID объекта
     * @param <T> Тип объекта
     * @return Объект
     */
    public static <T> T checkNotFoundWithId(T object, int id) {
        if (object == null) {
            throw new NotFoundException("Not found entity with " + "id=" + id);
        }
        return object;
    }
}
