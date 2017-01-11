package model;

/**
 * Created by dkarachurin on 09.01.2017.
 */
public class Person extends Staff implements Comparable<Person>{
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;

    public Person(int id, String firstName, String lastName, String middleName, String position) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return firstName  +
                " " + lastName +
                " " + middleName;
    }

    @Override
    public int compareTo(Person person) {
        return this.toString().compareTo(person.toString());
    }
}
