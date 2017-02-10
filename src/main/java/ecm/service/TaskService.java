package ecm.service;

import ecm.dao.GenericDAO;
import ecm.model.Task;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class TaskService extends GenericServiceImpl<Task> {

    public TaskService() {
    }

    @Override
    public List<Task> findAllSorted(String field, String direction) {
        switch (field){
            case "authorName": {
                field = "author.firstname";
                break;
            }
            case "executorName": {
                field = "executor.firstname";
                break;
            }
            case "controllerName": {
                field = "controller.firstname";
                break;
            }
        }
        return super.findAllSorted(field, direction);
    }
}
