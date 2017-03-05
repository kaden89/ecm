package ecm.service;

import ecm.model.Image;

import javax.ws.rs.NotFoundException;

/**
 * @author dkarachurin
 */
public interface ImageService {

    public Image findByOwnerId(int ownerId) throws NotFoundException;

    public Image save(Image image);

    public Image update(Image image);

    public void deleteByOwnerId(int ownerId) throws NotFoundException;

    public void deleteAll();
}
