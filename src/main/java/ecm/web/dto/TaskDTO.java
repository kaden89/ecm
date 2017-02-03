package ecm.web.dto;

import ecm.model.Task;

import java.time.LocalDate;

/**
 * Created by dkarachurin on 02.02.2017.
 */
public class TaskDTO extends AbstractDocumentDTO {
    private LocalDate dateOfIssue;
    private LocalDate deadline;
    private Integer executorId;
    private boolean isControlled;
    private Integer controllerId;

    public TaskDTO() {
    }

    public TaskDTO(Integer id, String name, String text, String regNumber, LocalDate date, Integer authorId, LocalDate dateOfIssue, LocalDate deadline, Integer executorId, boolean isControlled, Integer controllerId) {
        super(id, name, text, regNumber, date, authorId);
        this.dateOfIssue = dateOfIssue;
        this.deadline = deadline;
        this.executorId = executorId;
        this.isControlled = isControlled;
        this.controllerId = controllerId;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public TaskDTO(Task task) {
        super(task.getId(), task.getName(), task.getText(), task.getRegNumber(), task.getDate(), task.getAuthor().getId());
        this.dateOfIssue = task.getDateOfIssue();
        this.deadline = task.getDeadline();
        this.executorId = task.getExecutor().getId();
        this.isControlled = task.isControlled();
        this.controllerId = task.getController().getId();
        this.setFullname(task.toString());
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

    public Integer getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
    }

    public boolean isControlled() {
        return isControlled;
    }

    public void setControlled(boolean controlled) {
        isControlled = controlled;
    }

    public Integer getControllerId() {
        return controllerId;
    }

    public void setControllerId(Integer controllerId) {
        this.controllerId = controllerId;
    }
}
