package ecm.web;

import ecm.dao.DocumentDaoJpa;
import ecm.dao.GenericDAO;
import ecm.dao.PersonDaoJPA;
import ecm.model.Document;
import ecm.model.Person;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
    private GenericDAO<Person> personDAO;

    @Inject
    private GenericDAO<Document> documentDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(){
        GenericEntity<List<Person>> employees = new GenericEntity<List<Person>>(personDAO.findAll()) {
        };
        return Response.ok(employees).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") int employeeId){
        Person person = personDAO.find(employeeId);
        return Response.ok(person).build();
    }

    @GET
    @Path("/{id}/documents")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEmployeeDocuments(@PathParam("id") int employeeId){
        List<TreeSet<Document>> documents = new ArrayList<>(StartClass.result.values());
        Set<Document> set = documents.get(0);
        List<Document> list = new ArrayList<>(set);
        GenericEntity<List<Document>> data = new GenericEntity<List<Document>>(list) {
        };
        return Response.ok(data).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(Person person, @Context UriInfo uriInfo){
        //log.info("create organization "+organization);
        Person created = personDAO.save(person);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(created.getId()));
        return Response.created(builder.build()).entity(person).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(Person person, @PathParam("id") int personId){
        //log.info("update organization "+organization+" with id "+organizationId);
        Person updated =  personDAO.update(person);
        return Response.ok(updated).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int personId){
        //log.info("delete organization with id "+organizationId);
        personDAO.delete(personId);
        return Response.ok().build();
    }

}
