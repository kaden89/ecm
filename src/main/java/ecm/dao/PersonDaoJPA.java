package ecm.dao;

import ecm.model.Person;

import javax.ejb.Stateless;

/**
 *  JPA DAO для класса {@link Person}
 * @author dkarachurin
 */
@Stateless
public class PersonDaoJPA extends AbstractGenericDaoJpaImpl<Person> {
}
