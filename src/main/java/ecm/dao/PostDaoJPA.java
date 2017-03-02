package ecm.dao;

import ecm.model.Post;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 09.02.2017.
 */
@Stateless
public class PostDaoJPA extends AbstractGenericDaoJpaImpl<Post> {
}
