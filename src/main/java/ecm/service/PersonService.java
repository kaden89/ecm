package ecm.service;

import ecm.dao.GenericDAO;
import ecm.model.Person;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class PersonService extends GenericServiceImpl<Person>  {

    public PersonService() {
    }
}
