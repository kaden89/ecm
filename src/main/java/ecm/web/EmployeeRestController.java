package ecm.web;

import ecm.model.*;
import ecm.web.dto.AbstractStaffDTO;
import ecm.web.dto.PersonDTO;
import ecm.web.dto.TreeNode;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public Response getEmployees(@QueryParam("sortField") String field, @QueryParam("direction") String direction){
        GenericEntity<List<AbstractStaffDTO>> employees;
        if (field !=null) {
            employees = new GenericEntity<List<AbstractStaffDTO>>(new ArrayList<>(getStaffDTOConverter().toDtoCollection(new ArrayList<>(getPersonService().findAllSorted(field, direction))))) {
            };
        }
        else {
            employees = new GenericEntity<List<AbstractStaffDTO>>(new ArrayList<>(getStaffDTOConverter().toDtoCollection(new ArrayList<>(getPersonService().findAll())))) {
            };
        }
        int size = employees.getEntity().size();
        //TODO Paging need to implementing
     return Response.ok(employees).header( "Content-Range" , "items 0-"+size+"/"+size).build();
    }

    @GET
    @Path("/personTree")
    public Response getPersonRoot(){
        List<AbstractStaffDTO> dtos = new ArrayList<>(getStaffDTOConverter().toDtoCollection(new ArrayList<>(getPersonService().findAll())));
        TreeNode<AbstractStaffDTO> root = new TreeNode<>("Employees", "", dtos, "employees");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRoot(){
        List<AbstractStaffDTO> dtos = new ArrayList<>(getStaffDTOConverter().toDtoCollection(new ArrayList<>(getPersonService().findAll())));
        TreeNode<AbstractStaffDTO> root = new TreeNode<>("Employees", "root", dtos, "employees");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") int employeeId){
        AbstractStaffDTO person = getStaffDTOConverter().toDTO(getPersonService().find(employeeId));
        return Response.ok(person).build();
    }

//    @GET
//    @Path("/{id}/incoming")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getEmployeeIncomingDocuments(@PathParam("id") int employeeId){
//        GenericEntity<List<Incoming>> incomings = new GenericEntity<List<Incoming>>(incomingService.findAllByAuthorId(employeeId)) {
//        };
//        return Response.ok(incomings).build();
//    }
//
//    @GET
//    @Path("/{id}/outgoing")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getEmployeeOutgoingDocuments(@PathParam("id") int employeeId){
//        GenericEntity<List<Outgoing>> outgoings = new GenericEntity<List<Outgoing>>(outgoingService.findAllByAuthorId(employeeId)) {
//        };
//        return Response.ok(outgoings).build();
//    }
//
//    @GET
//    @Path("/{id}/task")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getEmployeeTaskDocuments(@PathParam("id") int employeeId){
//        GenericEntity<List<Task>> tasks = new GenericEntity<List<Task>>(taskService.findAllByAuthorId(employeeId)) {
//        };
//        return Response.ok(tasks).build();
//    }

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
    public Response updateEmployee(@PathParam("id") int id, PersonDTO dto){
        dto.setId(id);
        Person updated = getPersonService().update((Person) getStaffDTOConverter().fromDTO(dto));
        return Response.ok(getStaffDTOConverter().toDTO(updated)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(PersonDTO dto){
        dto.setId(null);
        Person person = getPersonService().save((Person) getStaffDTOConverter().fromDTO(dto));
        return Response.ok(getStaffDTOConverter().toDTO(person)).build();
    }



    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int personId){
        getPersonService().delete(getPersonService().find(personId));
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/photo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoto(@PathParam("id") int ownerId){
       return Response.ok(getImageService().findByOwnerId(ownerId)).build();
    }

    @POST
    @Path("/{id}/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPhoto(@PathParam("id") int ownerId,  @FormDataParam("files[]") File photo){
        byte[] bytes = null;
        Image result;
        try {
            bytes = Files.readAllBytes(Paths.get(photo.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = getImageService().findByOwnerId(ownerId);
        if (image==null){
            result = getImageService().save(new Image(getPersonService().find(ownerId), bytes));
        }
        else {
            image.setImage(bytes);
            result = getImageService().update(image);
        }

       return Response.ok(result).build();
    }
}
