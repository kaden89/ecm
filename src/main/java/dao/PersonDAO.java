package dao;

import model.Person;

import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public interface PersonDAO {
    List<Person> findAll();

    void addPerson(Person person);
}
