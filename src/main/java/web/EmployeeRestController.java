package web;

import dao.MemoryStore;
import dao.PersonDAO;
import model.Document;
import model.Person;
import util.xml.Persons;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Path(value = EmployeeRestController.REST_URL)
public class EmployeeRestController {
    static final String REST_URL = "/employees";
    @Inject
    private PersonDAO personDAO;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(){
        List<Person> employees = MemoryStore.personStore;
        Persons persons = new Persons(employees);
        GenericEntity<List<Person>> list = new GenericEntity<List<Person>>(employees) {
        };
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEmployeeDocuments(@PathParam("id") int employeeId){
        List<TreeSet<Document>> documents = new ArrayList<>(StartClass.result.values());
        Set<Document> set = documents.get(0);
        List<Document> list = new ArrayList<>(set);
        GenericEntity<List<Document>> data = new GenericEntity<List<Document>>(list) {
        };
        return Response.ok(data).build();
    }
}
