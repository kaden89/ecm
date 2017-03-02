package ecm.dao;

import ecm.model.Person;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Stateless
public class PersonDaoJPA extends AbstractGenericDaoJpaImpl<Person> {

}
