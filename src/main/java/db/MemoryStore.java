package db;

import model.Department;
import model.Organization;
import model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public class MemoryStore {
    public static List<Organization> organizationStore = new ArrayList<>();
    public static List<Department> departmentStore = new ArrayList<>();
    public static List<Person> personStore = new ArrayList<>();
}
