package ecm.web.dto.converters;

import ecm.model.Task;
import ecm.web.dto.TaskDTO;

import javax.inject.Singleton;

/**
 * Created by dkarachurin on 13.02.2017.
 */
@Singleton
public class TaskDTOConverter extends AbstractDocumentDTOConverterImpl<Task, TaskDTO> {
    @Override
    public Task fromDTO(TaskDTO dto) {
        Task task = new Task(dto);
        task.setAuthor(getPersonDAO().find(dto.getAuthorId()));
        task.setExecutor(getPersonDAO().find(dto.getExecutorId()));
        task.setController(getPersonDAO().find(dto.getControllerId()));
        return task;
    }

    @Override
    public TaskDTO toDTO(Task document) {
        TaskDTO taskDTO = new TaskDTO(document);
        return taskDTO;
    }
}
