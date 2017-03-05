package ecm.dao;

import ecm.model.Image;

/**
 * Интерфейс предоставляющий  методы доступа к данным для класса {@link Image}
 * @see ImageDaoJPA
 * @author dkarachurin
 */
public interface ImageDAO {
    /**
     * Поиск картинки по ID владельца
     * @param ownerId ID владельца картинки
     * @return Найденная картинка, либо null
     */
    Image findByOwnerId(int ownerId);

    /**
     * Сохранение картинки
     * @param image Картинка для сохранения в БД
     * @return Сохраненная картинка
     */
    Image save(Image image);

    /**
     * Обновление картинки
     * @param image Картинка для обновления
     * @return Обновленная картинка
     */
    Image update(Image image);

    /**
     * Удаление картинки по ID владельца
     * @param image Удаляемая картинка
     */
    void delete(Image image);

    /**
     * Удаление всех картинок из БД
     */
    void deleteAll();
}
