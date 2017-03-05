package ecm.service;

import ecm.model.Post;

import javax.ejb.Stateless;

/**
 * Слой сервиса реализующий доступ к DAO класса {@link Post}
 * @author dkarachurin
 */
@Stateless
public class PostService extends AbstractGenericServiceImpl<Post> {
}
