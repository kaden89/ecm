package dao;

import model.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Stateless
public class PersonDaoJPA extends GenericDaoJpa<Person>{
}
