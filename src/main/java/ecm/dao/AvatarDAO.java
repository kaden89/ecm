package ecm.dao;

import ecm.model.Avatar;

/**
 * Интерфейс предоставляющий  методы доступа к данным для класса {@link Avatar}
 * @see AvatarDaoJPA
 * @author dkarachurin
 */
public interface AvatarDAO {
    /**
     * Поиск аватара по ID владельца
     * @param ownerId ID владельца аватара
     * @return Найденный аватар, либо null
     */
    Avatar findByOwnerId(int ownerId);

    /**
     * Сохранение аватара
     * @param avatar Аватар для сохранения в БД
     * @return Сохраненный аватар
     */
    Avatar save(Avatar avatar);

    /**
     * Обновление аватара
     * @param avatar Аватар для обновления
     * @return Обновленный аватар
     */
    Avatar update(Avatar avatar);

    /**
     * Удаление картинки по ID владельца
     * @param avatar Удаляемый аватар
     */
    void delete(Avatar avatar);

    /**
     * Удаление всех аватаров из БД
     */
    void deleteAll();
}
