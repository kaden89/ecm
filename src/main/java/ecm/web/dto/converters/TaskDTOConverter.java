package ecm.web.dto.converters;

import ecm.model.Task;
import ecm.web.dto.TaskDTO;

import javax.inject.Singleton;

/**
 * Created by dkarachurin on 13.02.2017.
 */
@Singleton
public class TaskDTOConverter extends AbstractDocumentDTOConverterImpl<Task, TaskDTO> {
}
