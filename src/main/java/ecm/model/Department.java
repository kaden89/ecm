package ecm.model;

/**
 * Created by dkarachurin on 11.01.2017.
 */
public class Department extends Staff {
    private String fullName;
    private String shortName;
    private Person manager;
    private String contactsTelephones;

    public Department() {
    }

    public Department(Integer id, String fullName, String shortName, Person manager, String contactsTelephones) {
        super(id);
        this.fullName = fullName;
        this.shortName = shortName;
        this.manager = manager;
        this.contactsTelephones = contactsTelephones;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

    public String getContactsTelephones() {
        return contactsTelephones;
    }

    public void setContactsTelephones(String contactsTelephones) {
        this.contactsTelephones = contactsTelephones;
    }
}
