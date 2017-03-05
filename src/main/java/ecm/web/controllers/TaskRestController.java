package ecm.web.controllers;

import ecm.model.Task;
import ecm.web.dto.TaskDTO;

import javax.ws.rs.Path;

/**
 * Реализация rest контроллера для класса {@link Task}
 * @author dkarachurin
 */
@Path(value = TaskRestController.REST_URL)
public class TaskRestController extends AbstractGenericRestController<Task, TaskDTO> {
    public static final String REST_URL = "/tasks";
}
