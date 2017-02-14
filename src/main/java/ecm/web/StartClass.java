package ecm.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import ecm.model.*;
import ecm.model.documents_factory.DocumentsFactory;
import ecm.model.documents_factory.FactoryEnum;
import ecm.service.GenericService;
import ecm.util.exceptions.DocumentExistsException;
import ecm.web.dto.DepartmentsDTO;
import ecm.web.dto.OrganizationsDTO;
import ecm.web.dto.PersonsDTO;
import ecm.web.dto.converters.GenericDTOConverter;
import ecm.web.dto.PersonDTO;

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
import java.util.logging.Logger;

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
    private GenericDTOConverter<Person, PersonDTO> personDTOConverter;

    @Inject
    private transient Logger log;

    @Inject
    private DocumentsFactory factory;

    @Inject
    private GenericService<Person> personService;

    @Inject
    private GenericService<Outgoing> outgoingService;

    @Inject
    private GenericService<Incoming> incomingService;

    @Inject
    private GenericService<Task> taskService;

    @Inject
    private GenericService<Post> postService;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        context = servletContextEvent.getServletContext();

        Post post1 = new Post("tester");
        Post post2 = new Post("programmer");
        Post post3 = new Post("manager");

        postService.save(post1);
        postService.save(post2);
        postService.save(post3);

        deleteOldDocuments();
        loadStaff();
        try {
            generateDocuments();
        } catch (InstantiationException e) {
            log.warning(e.getMessage());
        } catch (IllegalAccessException e) {
            log.warning(e.getMessage());
        }


        Person test = new Person("test person", "without", "documents", post1, LocalDate.now());
        Person test2 = new Person("test person2", "without", "documents", post2, LocalDate.now());
        personService.save(test);
        personService.save(test2);
//        writeJSONtoDisk();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void deleteOldDocuments() {
        outgoingService.deleteAll();
        incomingService.deleteAll();
        taskService.deleteAll();
        personService.deleteAll();
    }

    private void generateDocuments() throws InstantiationException, IllegalAccessException {
        for (int i = 0; i < 30; i++) {
            try {
                Incoming incoming = (Incoming) createDocument(INCOMING);
                incomingService.save(incoming);

                Outgoing outgoing = (Outgoing) createDocument(OUTGOING);
                outgoingService.save(outgoing);

                Task task = (Task) createDocument(TASK);
                taskService.save(task);

            } catch (DocumentExistsException e) {
                log.warning(e.getMessage());
            }
        }

        for (Map.Entry<Person, TreeSet<Document>> entry : result.entrySet()) {
            System.out.println(entry.getKey());
            TreeSet<Document> documents = entry.getValue();
            for (Document document : documents) {
                log.info("    " + document);
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


    private void writeJSONtoDisk() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String dirName = "C:\\reports\\";
        File theDir = new File(dirName);

        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                log.warning(se.getMessage());
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
                log.warning(e.getMessage());
            }
        }

    }

    private void loadStaff() {
        try (
                InputStream personsStream = context.getResourceAsStream("/resources/xml/persons.xml");
//                InputStream organizationsStream = context.getResourceAsStream("/resources/xml/organizations.xml");
//                InputStream departmentsStream = context.getResourceAsStream("/resources/xml/departments.xml")
        ) {
            JAXBContext context = JAXBContext.newInstance(OrganizationsDTO.class, DepartmentsDTO.class, PersonsDTO.class, PersonDTO.class);
            Unmarshaller um = context.createUnmarshaller();
            PersonsDTO persons = (PersonsDTO) um.unmarshal(personsStream);

            Collection<Person> personList = personDTOConverter.fromDtoCollection(persons.getPersons());

            for (Person person : personList) {
                personService.save(person);
                result.put(person, new TreeSet<>());
            }

        } catch (JAXBException e) {
            log.warning(e.getMessage());
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
    }
}
