package ecm.web.dto;

import ecm.model.Task;

import java.time.LocalDate;

/**
 * ДТО {@link Task}
 * @author dkarachurin
 */
public class TaskDTO extends AbstractDocumentDTO {
    private LocalDate dateOfIssue;
    private LocalDate deadline;
    private Integer executorId;
    private String executorName;
    private Integer controllerId;
    private String controllerName;
    private boolean isControlled;
    private String type = "task";

    public TaskDTO() {
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public TaskDTO(Task task) {
        super(task);
        this.dateOfIssue = task.getDateOfIssue();
        this.deadline = task.getDeadline();
        this.executorId = task.getExecutor().getId();
        this.executorName = task.getExecutor().toString();
        this.controllerId = task.getController().getId();
        this.controllerName = task.getController().toString();
        this.isControlled = task.isControlled();
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

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
