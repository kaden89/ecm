package ecm.dao;

import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Person;
import ecm.model.Task;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Singleton
@Transactional
public class PersonDaoJPA extends GenericDaoJpa<Person>{
    @Override
    public void delete(int id){
        Person person = find(id);

        super.delete(id);
    }
}
