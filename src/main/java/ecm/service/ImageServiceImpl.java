package ecm.service;

import ecm.dao.ImageDAO;
import ecm.model.Image;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by dkarachurin on 09.02.2017.
 */
@Stateless
public class ImageServiceImpl implements ImageService {
    @Inject
    private ImageDAO imageDAO;

    @Override
    public Image findByOwnerId(int ownerId) {
        return imageDAO.findByOwnerId(ownerId);
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
    public void deleteByOwnerId(int id) {
        imageDAO.deleteByOwnerId(id);
    }

    @Override
    public void deleteAll() {
        imageDAO.deleteAll();
    }
}
