package ecm.dao;

import ecm.model.Post;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 09.02.2017.
 */
@Singleton
@Transactional
public class PostDaoJPA extends AbstractGenericDaoJpaImpl<Post> {
}
