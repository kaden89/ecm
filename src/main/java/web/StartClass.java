package web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dao.MemoryStore;
import model.Document;
import model.Person;
import model.documents_factory.DocumentsFactory;
import model.documents_factory.FactoryEnum;
import util.xml.Departments;
import util.xml.Organizations;
import util.xml.Persons;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static model.documents_factory.FactoryEnum.INCOMING;
import static model.documents_factory.FactoryEnum.OUTGOING;
import static model.documents_factory.FactoryEnum.TASK;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public class StartClass implements ServletContextListener {
    private DocumentsFactory factory = DocumentsFactory.INSTANCE;
    public static TreeMap<Person, TreeSet<Document>> result = new TreeMap<>();
    private ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        context = servletContextEvent.getServletContext();
        loadStaff();
        try {
            generateDocuments();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        createJSON();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void generateDocuments() throws InstantiationException, IllegalAccessException {
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
    private void createDocument(FactoryEnum factoryEnum) throws IllegalAccessException, InstantiationException {
        Document document = factory.createDocument(factoryEnum);
        Person author = document.getAuthor();
        if (result.containsKey(author)){
            result.get(author).add(document);
        }
        else {
            result.put(author, new TreeSet<>(Arrays.asList(document)));
        }
    }



    private void createJSON(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        for (Map.Entry<Person, TreeSet<Document>> entry : result.entrySet()) {
            try (Writer writer = new FileWriter("C:\\reports\\"+entry.getKey().toString()+".json")) {
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

    private void loadStaff(){
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

            Departments departments = (Departments) um.unmarshal(departmentsStream);
            MemoryStore.departmentStore = departments.getDepartments();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
