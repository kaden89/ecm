package ecm.service;

import ecm.model.Task;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Created by dkarachurin on 08.02.2017.
 */
@Singleton
@Transactional
public class TaskService extends AbstractGenericServiceImpl<Task> {

    public TaskService() {
    }

    @Override
    public Page<Task> findAllSortedAndPageable(Sort sort, RangeHeader range) {
        switch (sort.getField()) {
            case "authorName": {
                sort.setField("author.fullname");
                break;
            }
            case "executorName": {
                sort.setField("executor.fullname");
                break;
            }
            case "controllerName": {
                sort.setField("controller.fullname");
                break;
            }
        }
        return super.findAllSortedAndPageable(sort, range);
    }
}
