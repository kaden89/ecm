package ecm.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import ecm.dao.GenericDAO;
import ecm.dao.MemoryStore;
import ecm.dao.PersonDaoJPA;
import ecm.model.*;
import ecm.model.documents_factory.DocumentsFactory;
import ecm.model.documents_factory.FactoryEnum;
import ecm.util.exceptions.DocumentExistsException;
import ecm.util.xml.Departments;
import ecm.util.xml.Organizations;
import ecm.util.xml.Persons;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static ecm.model.documents_factory.FactoryEnum.INCOMING;
import static ecm.model.documents_factory.FactoryEnum.OUTGOING;
import static ecm.model.documents_factory.FactoryEnum.TASK;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public class StartClass implements ServletContextListener {

    public static TreeMap<Person, TreeSet<Document>> result = new TreeMap<>();
    private ServletContext context;

    @Inject
    private DocumentsFactory factory;

    @Inject
    private GenericDAO<Person> personDAO;

    @Inject
    private GenericDAO<Outgoing> outgoingDAO;

    @Inject
    private GenericDAO<Incoming> incomingDAO;

    @Inject
    private GenericDAO<Task> taskDAO;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        context = servletContextEvent.getServletContext();
        deleteOldDocuments();
        loadStaff();
        try {
            generateDocuments();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Person test = new Person("test", "test", "testovich", "ttt", new byte[0], LocalDate.now());
        personDAO.save(test);
//        createJSON();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void deleteOldDocuments() {
        outgoingDAO.deleteAll();
        incomingDAO.deleteAll();
        taskDAO.deleteAll();
        personDAO.deleteAll();
    }

    private void generateDocuments() throws InstantiationException, IllegalAccessException {
        for (int i = 0; i < 10; i++) {
            try {
                Incoming incoming = (Incoming) createDocument(INCOMING);
                incomingDAO.save(incoming);
            } catch (DocumentExistsException e) {
                e.printStackTrace();
            }
            try {
                Outgoing outgoing = (Outgoing) createDocument(OUTGOING);
                outgoingDAO.save(outgoing);
            } catch (DocumentExistsException e) {
                e.printStackTrace();
            }
            try {
                Task task = (Task) createDocument(TASK);
                taskDAO.save(task);
            } catch (DocumentExistsException e) {
                e.printStackTrace();
            }
        }

        for (Map.Entry<Person, TreeSet<Document>> entry : result.entrySet()) {
            System.out.println(entry.getKey());
            TreeSet<Document> documents = entry.getValue();
            for (Document document : documents) {
                System.out.println("    " + document);
            }
        }
    }

    private Document createDocument(FactoryEnum factoryEnum) throws IllegalAccessException, InstantiationException, DocumentExistsException {
        Document document = factory.createDocument(factoryEnum);
        Person author = document.getAuthor();
        if (result.containsKey(author)) {
            result.get(author).add(document);
        } else {
            result.put(author, new TreeSet<>(Arrays.asList(document)));
        }
        return document;
    }


    private void createJSON() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String dirName = "C:\\reports\\";
        File theDir = new File(dirName);

        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        for (Map.Entry<Person, TreeSet<Document>> entry : result.entrySet()) {
            try (Writer writer = new FileWriter(dirName + entry.getKey().toString() + ".json")) {
                for (Document document : entry.getValue()) {
                    JsonElement jsonElement = gson.toJsonTree(document);
                    jsonElement.getAsJsonObject().addProperty("type", document.getClass().getSimpleName());
                    gson.toJson(jsonElement, writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadStaff() {
        try {
            InputStream personsStream = context.getResourceAsStream("/resources/xml/persons.xml");
            InputStream organizationsStream = context.getResourceAsStream("/resources/xml/organizations.xml");
            InputStream departmentsStream = context.getResourceAsStream("/resources/xml/departments.xml");

            JAXBContext context = JAXBContext.newInstance(Organizations.class, Departments.class, Persons.class, Person.class);
            Unmarshaller um = context.createUnmarshaller();

            Organizations organizations = (Organizations) um.unmarshal(organizationsStream);
            MemoryStore.organizationStore = organizations.getOrganizations();

            Persons persons = (Persons) um.unmarshal(personsStream);
            MemoryStore.personStore = persons.getPersons();
            List<Person> personList = persons.getPersons();

            List<Person> tmp = personDAO.findAll();
//            for (Person person : tmp) {
//                personDAO.delete(person.getId());
//            }
            for (Person person : personList) {
                personDAO.save(person);
                //TODO костыль
                result.put(person, new TreeSet<>());
            }

            Departments departments = (Departments) um.unmarshal(departmentsStream);
            MemoryStore.departmentStore = departments.getDepartments();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
