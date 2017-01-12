import db.MemoryStore;
import model.Department;
import model.Document;
import model.Person;
import model.documents_factory.DocumentsFactory;
import model.documents_factory.FactoryEnum;
import util.xml.Departments;
import util.xml.Organizations;
import util.xml.Persons;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;

import static model.documents_factory.FactoryEnum.*;


public class Main {
    private static DocumentsFactory factory = DocumentsFactory.INSTANCE;
    private static TreeMap<Person, TreeSet<Document>> result = new TreeMap<>();

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, JAXBException {

        loadStaff();

        for (int i = 0; i < 10; i++) {
            createDocument(INCOMING);
            createDocument(OUTGOING);
            createDocument(TASK);
        }

        for (Map.Entry<Person, TreeSet<Document>> entry : result.entrySet()) {
            System.out.println(entry.getKey());
            TreeSet<Document> documents = entry.getValue();
            for (Document document : documents) {
                System.out.println("    "+document);
            }
        }

    }

    private static void createDocument(FactoryEnum factoryEnum) throws IllegalAccessException, InstantiationException {
       Document document = factory.createDocument(factoryEnum);
       Person author = document.getAuthor();
       if (result.containsKey(author)){
           result.get(author).add(document);
       }
       else {
           result.put(author, new TreeSet<>(Arrays.asList(document)));
       }
    }

    private static void loadStaff(){
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File personsFile = new File(classLoader.getResource("xml/persons.xml").getFile());
            File organizationsFile = new File(classLoader.getResource("xml/organizations.xml").getFile());
            File departmentsFile = new File(classLoader.getResource("xml/departments.xml").getFile());

            JAXBContext context = JAXBContext.newInstance(Organizations.class, Departments.class, Persons.class, Person.class);
            Unmarshaller um = context.createUnmarshaller();

            Organizations organizations = (Organizations) um.unmarshal(organizationsFile);
            MemoryStore.organizationStore = organizations.getOrganizations();

            Persons persons = (Persons) um.unmarshal(personsFile);
            MemoryStore.personStore = persons.getPersons();

            Departments departments = (Departments) um.unmarshal(departmentsFile);
            MemoryStore.departmentStore = departments.getDepartments();

            int a = 1;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
