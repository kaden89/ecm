package ecm.dao;

import ecm.model.Incoming;
import ecm.model.Task;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 16.01.2017.
 */
@Singleton
@Transactional
public class TaskDaoJPA extends DocumentGenericDaoJpa<Task> {
    @Override
    public List<Task> findAllWithPersonId(int id) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e where e.author.id = " + id
                +" OR e.executor.id = "+ id + " OR e.controller.id = "+ id).getResultList();
    }
}
