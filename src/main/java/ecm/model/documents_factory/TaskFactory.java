package ecm.model.documents_factory;

import ecm.model.Document;
import ecm.model.Task;
import ecm.model.documents_factory.util.DocumentPopulator;
import ecm.util.exceptions.DocumentExistsException;

import javax.inject.Inject;
import java.time.LocalDate;

import static ecm.model.documents_factory.util.DocumentPopulator.*;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class TaskFactory extends AbstractDocumentsFactory {
    @Inject
    DocumentPopulator populator;

    @Override
    public Document createDocument() throws DocumentExistsException {
        Task task = new Task();
        populator.populateBasicsOfDocument(task);
        task.setController(populator.getRandomPerson());
        task.setExecutor(populator.getRandomPerson());
        task.setDateOfIssue(populator.getRandomDate(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)));
        task.setDeadline(populator.getRandomDate(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)));
        task.setControlled(populator.getRandomBoolean());
        return task;
    }
}
