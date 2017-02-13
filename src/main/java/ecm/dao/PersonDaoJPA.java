package ecm.dao;

import ecm.model.*;

import javax.inject.Singleton;
import javax.transaction.*;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Singleton
@Transactional
public class PersonDaoJPA extends AbstractGenericDaoJpaImpl<Person> {

}
