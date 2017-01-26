package ecm.web;

import com.google.gson.Gson;
import ecm.dao.GenericDAO;
import ecm.model.*;
import ecm.util.TreeNode;
import ecm.util.exceptions.HasLinksException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import javax.transaction.TransactionalException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Path(value = EmployeeRestController.REST_URL)
public class EmployeeRestController extends AbstractRestController{
    static final String REST_URL = "/employees";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(){
        GenericEntity<List<Person>> employees = new GenericEntity<List<Person>>(personDAO.findAll()) {
        };
        int size = employees.getEntity().size();
        //TODO Paging need to implementing
        String jsonInString = gson.toJson(personDAO.findAll());
        return Response.ok(toJson(personDAO.findAll())).header( "Content-Range" , "items 0-"+size+"/"+size).build();
    }

    @GET
    @Path("/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRoot(){
        TreeNode<Person> root = new TreeNode<>("Employees", "root", personDAO.findAll());
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
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

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, Person person){
        person.setId(id);
        Person updated = personDAO.update(person);
        return Response.ok(updated).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(@FormDataParam("id") int personId,
                                   @FormDataParam("uploadedfile") File file,
                                   @FormDataParam("firstname") String firstname,
                                   @FormDataParam("surname") String surname,
                                   @FormDataParam("patronymic") String patronymic,
                                   @FormDataParam("position") String position,
                                   @FormDataParam("birthday") String birthday){
        byte[] photo = null;

        try {
            photo = Files.readAllBytes(Paths.get(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Person person = new Person(firstname, surname, patronymic, position, photo, LocalDate.parse(birthday));
        if (personId==0) {
            person = personDAO.save(person);
        }
        else {
            person.setId(personId);
            personDAO.update(person);
        }
        return Response.ok(person).build();
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

    @GET
    @Path("/{id}/photo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoto(@PathParam("id") int ownerId){
       return Response.ok(imageDAO.findByOwnerId(ownerId)).build();
    }

    @POST
    @Path("/{id}/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPhoto(@PathParam("id") int ownerId,  @FormDataParam("uploadedfile") File photo){
        byte[] bytes = null;
        Image result = null;
        try {
            bytes = Files.readAllBytes(Paths.get(photo.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = imageDAO.findByOwnerId(ownerId);
        if (image==null){
            result = imageDAO.save(new Image(personDAO.find(ownerId), bytes));
        }
        else {
            image.setImage(bytes);
            result = imageDAO.update(image);
        }

       return Response.ok(result).build();
    }
    @PUT
    @Path("/{id}/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePhoto(@PathParam("id") int ownerId,  @FormDataParam("uploadedfile") File photo){
        byte[] bytes = null;

        try {
            bytes = Files.readAllBytes(Paths.get(photo.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = imageDAO.findByOwnerId(ownerId);
        image.setImage(bytes);
       return Response.ok(imageDAO.update(image)).build();
    }
}
