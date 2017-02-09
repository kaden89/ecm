package ecm.dao;

import ecm.model.Image;

/**
 * Created by dkarachurin on 09.02.2017.
 */
public interface ImageDAO {
    public Image findByOwnerId(int ownerId);

    public Image save(Image image);

    public Image update(Image image);

    public Image saveOrUpdate(Image image);

    public void deleteByOwnerId(int id);

    public void deleteAll();
}
