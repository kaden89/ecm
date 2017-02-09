package ecm.service;

import ecm.dao.ImageDAO;
import ecm.model.Image;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 09.02.2017.
 */
@Singleton
@Transactional
public class ImageServiceImpl implements ImageService{
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
    public Image saveOrUpdate(Image image) {
        return imageDAO.saveOrUpdate(image);
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
