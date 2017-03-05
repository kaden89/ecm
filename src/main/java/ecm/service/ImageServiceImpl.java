package ecm.service;

import ecm.dao.ImageDAO;
import ecm.model.Image;
import ecm.util.db.DbUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author dkarachurin
 */
@Stateless
public class ImageServiceImpl implements ImageService {
    @Inject
    private ImageDAO imageDAO;

    @Override
    public Image findByOwnerId(int ownerId) {
        return DbUtils.checkNotFoundWithId(imageDAO.findByOwnerId(ownerId), ownerId);
    }

    @Override
    public Image save(Image image) {
        return imageDAO.save(image);
    }

    @Override
    public Image update(Image image) {
        return imageDAO.update(image);
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
