package ecm.service;

import ecm.model.Avatar;

import javax.ws.rs.NotFoundException;

/**
 * Интерфейс для доступа к DAO аватара класса {@link ecm.model.Person}
 * @author dkarachurin
 */
public interface AvatarService {

    /**
     * Находит аватар по ID {@link ecm.model.Person}
     * @param ownerId ID класса {@link ecm.model.Person}
     * @return Аватар
     */
    public Avatar findByOwnerId(int ownerId);

    /**
     * Сохраняет аватар в БД
     * @param avatar Аватар
     * @return Сохраненный аватар
     */
    public Avatar save(Avatar avatar);

    /**
     * Обновляет аватар
     * @param avatar Обновляемый аватар
     * @return Обновленный аватар
     */
    public Avatar update(Avatar avatar);

    /**
     * Удаляет аватар по ID {@link ecm.model.Person}
     * @param ownerId ID владельца аватара
     * @throws NotFoundException В случае если аватар не найден
     */
    public void deleteByOwnerId(int ownerId) throws NotFoundException;

    /**
     * Удаляет все аватары из БД
     */
    public void deleteAll();
}
