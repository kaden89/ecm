package ecm.dao;

import ecm.model.Image;

/**
 * Created by dkarachurin on 09.02.2017.
 */
public interface ImageDAO {
    Image findByOwnerId(int ownerId);

    Image save(Image image);

    Image update(Image image);

    void deleteByOwnerId(int id);

    void deleteAll();
}
