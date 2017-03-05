package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Task;
import ecm.util.exceptions.DocumentExistsException;

import java.time.LocalDate;

/**
 * Фабрика документа Задача
 * @author dkarachurin
 */
public class TaskFactory extends AbstractDocumentsFactory {

    @Override
    public Document createDocument() throws DocumentExistsException {
        Task task = new Task();
        getPopulator().populateBasicsOfDocument(task);
        task.setController(getPopulator().getRandomPerson());
        task.setExecutor(getPopulator().getRandomPerson());
        task.setDateOfIssue(getPopulator().getRandomDate(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 31)));
        task.setDeadline(getPopulator().getRandomDate(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 31)));
        task.setControlled(getPopulator().getRandomBoolean());
        return task;
    }
}
