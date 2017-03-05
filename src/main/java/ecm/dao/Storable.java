package ecm.dao;

/**
 * Интерфейс маркирующий класс как сохраняющийся в БД
 * @author dkarachurin
 */
public interface Storable {
    /**
     * Получает ID объекта
     * @return ID объекта
     */
    Integer getId();

    /**
     * Возвращает имя таблицы объектов в БД
     * @return имя таблицы в БД
     */
    String getStorageName();
}
