package ecm.dao;

import ecm.model.Post;

import javax.ejb.Stateless;

/**
 *  JPA DAO для класса {@link Post}
 * @author dkarachurin
 */
@Stateless
public class PostDaoJPA extends AbstractGenericDaoJpaImpl<Post> {
}
