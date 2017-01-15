package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Task;
import ecm.util.exceptions.DocumentExistsException;

import java.time.LocalDate;

import static ecm.model.documents_factory.util.DocumentPopulator.*;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class TaskFactory extends AbstractDocumentsFactory {
    @Override
    public Document createDocument() throws DocumentExistsException {
        Task task = new Task();
        populateBasicsOfDocument(task);
        task.setController(getRandomPerson());
        task.setExecutor(getRandomPerson());
        task.setDateOfIssue(getRandomDate(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)));
        task.setDeadline(getRandomDate(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)));
        task.setControlled(getRandomBoolean());
        return task;
    }
}
