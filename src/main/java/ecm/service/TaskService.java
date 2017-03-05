package ecm.service;

import ecm.model.Task;

import javax.ejb.Stateless;

/**
 * Слой сервиса реализующий доступ к DAO класса {@link Task}
 * @author dkarachurin
 */
@Stateless
public class TaskService extends AbstractGenericServiceImpl<Task> {
}
