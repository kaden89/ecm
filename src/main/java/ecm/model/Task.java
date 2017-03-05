package ecm.model;

import ecm.web.dto.TaskDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Модель документа Задача
 * @author dkarachurin
 */
@Entity
public class Task extends Document {
    private LocalDate dateOfIssue;
    @NotNull
    private LocalDate deadline;
    @ManyToOne
    @NotNull
    private Person executor;
    private boolean isControlled;
    @ManyToOne
    @NotNull
    private Person controller;

    public Task() {
    }

    public Task(String name, String text, String regNumber, LocalDate date, Person author, LocalDate dateOfIssue, LocalDate deadline, Person executor, boolean isControlled, Person controller) {
        super(name, text, regNumber, date, author);
        this.dateOfIssue = dateOfIssue;
        this.deadline = deadline;
        this.executor = executor;
        this.isControlled = isControlled;
        this.controller = controller;
    }

    public Task(TaskDTO dto) {
        this(dto.getName(), dto.getText(), dto.getRegNumber(), dto.getDate(), null, dto.getDateOfIssue(), dto.getDeadline(), null, dto.isControlled(), null);
        this.setId(dto.getId());
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
        return "Task №" + super.toString();
    }
}
