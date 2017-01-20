package ecm.web;

import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import ecm.dao.GenericDAO;
import ecm.dao.TaskDaoJPA;
import ecm.model.*;
import ecm.util.exceptions.HasLinksException;
import org.hibernate.exception.ConstraintViolationException;
import sun.misc.IOUtils;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.mail.internet.MimeMultipart;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.TransactionalException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Path(value = EmployeeRestController.REST_URL)
public class EmployeeRestController {
    static final String REST_URL = "/employees";

    @Inject
    private GenericDAO<Person> personDAO;

    @Inject
    private GenericDAO<Outgoing> outgoingDAO;

    @Inject
    private GenericDAO<Incoming> incomingDAO;

    @Inject
    private GenericDAO<Task> taskDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(){
        GenericEntity<List<Person>> employees = new GenericEntity<List<Person>>(personDAO.findAll()) {
        };
        int size = employees.getEntity().size();
        return Response.ok(employees).header( "Content-Range" , "items 0-"+size+"/"+size).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") int employeeId){
        Person person = personDAO.find(employeeId);
        return Response.ok(person).build();
    }

    @GET
    @Path("/{id}/incoming")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeIncomingDocuments(@PathParam("id") int employeeId){
        GenericEntity<List<Incoming>> incomings = new GenericEntity<List<Incoming>>(incomingDAO.findAllByAuthorId(employeeId)) {
        };
        return Response.ok(incomings).build();
    }

    @GET
    @Path("/{id}/outgoing")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeOutgoingDocuments(@PathParam("id") int employeeId){
        GenericEntity<List<Outgoing>> outgoings = new GenericEntity<List<Outgoing>>(outgoingDAO.findAllByAuthorId(employeeId)) {
        };
        return Response.ok(outgoings).build();
    }

    @GET
    @Path("/{id}/task")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTaskDocuments(@PathParam("id") int employeeId){
        GenericEntity<List<Task>> tasks = new GenericEntity<List<Task>>(taskDAO.findAllByAuthorId(employeeId)) {
        };
        return Response.ok(tasks).build();
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee2(@FormParam("firstname") String firstname,
                                    @FormParam("surname") String surname,
                                    @FormParam("patronymic") String patronymic,
                                    @FormParam("position") String position,
                                    @FormParam("birthday") String birthday){


        Person person = new Person(null, firstname, surname, patronymic, position, null, LocalDate.parse(birthday));
        person = personDAO.save(person);
        return Response.ok(person).build();
    }
//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createEmployee(@FormDataParam("uploadedfile") InputStream stream, @Context UriInfo uriDetails){
//        byte[] targetArray = null;
//        try {
//            targetArray = new byte[stream.available()];
//            stream.read(targetArray);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Response.ok().build();
//    }

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
//        incomingDAO.deleteAll();
//        outgoingDAO.deleteAll();
//        taskDAO.deleteAll();
        try {
            personDAO.delete(personId);
        }
        catch (TransactionalException e){
            if (e.getCause().getCause().getCause() instanceof ConstraintViolationException)
                throw new HasLinksException("Can't delete person, because he has links to some documents!");
        }
        return Response.ok().build();
    }

//    @GET
//    @Path("/delete")
//    public void delete(){
//        incomingDAO.deleteAll();
//        outgoingDAO.deleteAll();
//        taskDAO.deleteAll();
//    }
}
