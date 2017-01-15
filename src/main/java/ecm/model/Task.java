package ecm.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@XmlRootElement(name = "Task")
public class Task extends Document {
    private LocalDate dateOfIssue;
    private LocalDate deadline;
    private Person executor;
    private boolean isControlled;
    private Person controller;

    public Task() {
    }

    public Task(int id, String name, String text, String regNumber, LocalDate date, Person author, LocalDate dateOfIssue, LocalDate deadline, Person executor, boolean isControlled, Person controller) {
        super(id, name, text, regNumber, date, author);
        this.dateOfIssue = dateOfIssue;
        this.deadline = deadline;
        this.executor = executor;
        this.isControlled = isControlled;
        this.controller = controller;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Person getExecutor() {
        return executor;
    }

    public void setExecutor(Person executor) {
        this.executor = executor;
    }

    public boolean isControlled() {
        return isControlled;
    }

    public void setControlled(boolean controlled) {
        isControlled = controlled;
    }

    public Person getController() {
        return controller;
    }

    public void setController(Person controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return "Task №"+super.toString();
    }
}
