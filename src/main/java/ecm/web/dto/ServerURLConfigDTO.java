package ecm.web.dto;

/**
 * Класс для отправки на клиент конфигурации URL
 * @author dkarachurin
 */
public class ServerURLConfigDTO {
    private String employeeURL = "rest/employees";
    private String taskURL = "rest/tasks";
    private String incomingURL = "rest/incomings";
    private String outgoingURL = "rest/outgoings";
    private String employeeTree = "rest/employees/tree";
    private String outgoingTree = "rest/outgoings/tree";
    private String incomingTree = "rest/incomings/tree";
    private String taskTree = "rest/tasks/tree";
    private String postURL = "rest/employees/posts";

    public ServerURLConfigDTO() {
    }

    public String getEmployeeURL() {
        return employeeURL;
    }

    public void setEmployeeURL(String employeeURL) {
        this.employeeURL = employeeURL;
    }

    public String getTaskURL() {
        return taskURL;
    }

    public void setTaskURL(String taskURL) {
        this.taskURL = taskURL;
    }

    public String getIncomingURL() {
        return incomingURL;
    }

    public void setIncomingURL(String incomingURL) {
        this.incomingURL = incomingURL;
    }

    public String getOutgoingURL() {
        return outgoingURL;
    }

    public void setOutgoingURL(String outgoingURL) {
        this.outgoingURL = outgoingURL;
    }

    public String getEmployeeTree() {
        return employeeTree;
    }

    public void setEmployeeTree(String employeeTree) {
        this.employeeTree = employeeTree;
    }

    public String getOutgoingTree() {
        return outgoingTree;
    }

    public void setOutgoingTree(String outgoingTree) {
        this.outgoingTree = outgoingTree;
    }

    public String getIncomingTree() {
        return incomingTree;
    }

    public void setIncomingTree(String incomingTree) {
        this.incomingTree = incomingTree;
    }

    public String getTaskTree() {
        return taskTree;
    }

    public void setTaskTree(String taskTree) {
        this.taskTree = taskTree;
    }

    public String getPostURL() {
        return postURL;
    }

    public void setPostURL(String postURL) {
        this.postURL = postURL;
    }
}
