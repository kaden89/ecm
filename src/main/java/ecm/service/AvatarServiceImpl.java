package ecm.service;

import ecm.dao.AvatarDAO;
import ecm.model.Avatar;
import ecm.util.db.DbUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Слой сервиса реализующий доступ к DAO класса {@link Avatar}
 * @author dkarachurin
 */
@Stateless
public class AvatarServiceImpl implements AvatarService {
    @Inject
    private AvatarDAO imageDAO;

    @Override
    public Avatar findByOwnerId(int ownerId) {
        return imageDAO.findByOwnerId(ownerId);
    }

    @Override
    public Avatar save(Avatar avatar) {
        return imageDAO.save(avatar);
    }

    @Override
    public Avatar update(Avatar avatar) {
        return imageDAO.update(avatar);
    }

    @Override
    public void deleteByOwnerId(int ownerId) {
        imageDAO.delete(DbUtils.checkNotFoundWithId(imageDAO.findByOwnerId(ownerId), ownerId));
    }

    @Override
    public void deleteAll() {
        imageDAO.deleteAll();
    }
}
