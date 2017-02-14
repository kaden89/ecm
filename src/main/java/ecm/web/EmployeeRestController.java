package ecm.web;

import ecm.model.*;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;
import ecm.web.dto.AbstractStaffDTO;
import ecm.web.dto.PersonDTO;
import ecm.web.dto.TreeNode;
import ecm.util.filtering.Filter;
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
public class EmployeeRestController extends AbstractRestController {
    static final String REST_URL = "/employees";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@HeaderParam("Range") RangeHeader range, @QueryParam("sort") Sort sort, @QueryParam("filter") String filterString) {
        if (filterString != null && sort != null && range != null) {
            Filter filter = (Filter) fromJson(filterString, Filter.class);
            Page<Person> page = getPersonService().findAllSortedFilteredAndPageable(sort, filter, range);
            return getPageResponseFromClass(page, Person.class);
        } else if (sort != null && range != null) {
            Page<Person> page = getPersonService().findAllSortedAndPageable(sort, range);
            return getPageResponseFromClass(page, Person.class);
        } else {
            Collection<PersonDTO> employees = getPersonDTOConverter().toDtoCollection(getPersonService().findAll());
            return Response.ok(toJson(employees)).header("Content-Range", "items 0-" + employees.size() + "/" + employees.size()).build();
        }
    }

    @GET
    @Path("/posts")
    public Response getPosts() {
        GenericEntity<List<Post>> posts = new GenericEntity<List<Post>>(new ArrayList<>(new ArrayList<>(getPostService().findAll()))) {
        };
        int size = posts.getEntity().size();
        return Response.ok(posts).header("Content-Range", "items 0-" + size + "/" + size).build();
    }

    @GET
    @Path("/posts/{id}")
    public Response getPosts(@PathParam("id") int postId) {
        return Response.ok(getPostService().find(postId)).build();
    }

    @GET
    @Path("/personTree")
    public Response getPersonRoot() {
        List<AbstractStaffDTO> dtos = new ArrayList<>(getPersonDTOConverter().toDtoCollection(new ArrayList<>(getPersonService().findAll())));
        TreeNode<AbstractStaffDTO> root = new TreeNode<>("Employees", "", dtos, "employees");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRoot() {
        List<AbstractStaffDTO> dtos = new ArrayList<>(getPersonDTOConverter().toDtoCollection(new ArrayList<>(getPersonService().findAll())));
        TreeNode<AbstractStaffDTO> root = new TreeNode<>("Employees", "root", dtos, "employees");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") int employeeId) {
        AbstractStaffDTO person = getPersonDTOConverter().toDTO(getPersonService().find(employeeId));
        return Response.ok(person).build();
    }

    @GET
    @Path("/{id}/documents")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEmployeeDocuments(@PathParam("id") int employeeId) {
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
    public Response updateEmployee(@PathParam("id") int id, PersonDTO dto) {
        dto.setId(id);
        Person updated = getPersonService().update((Person) getPersonDTOConverter().fromDTO(dto));
        return Response.ok(getPersonDTOConverter().toDTO(updated)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(PersonDTO dto) {
        dto.setId(null);
        Person person = getPersonService().save((Person) getPersonDTOConverter().fromDTO(dto));
        return Response.ok(getPersonDTOConverter().toDTO(person)).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int personId) {
        getPersonService().delete(personId);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/photo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoto(@PathParam("id") int ownerId) {
        return Response.ok(getImageService().findByOwnerId(ownerId)).build();
    }

    @POST
    @Path("/{id}/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPhoto(@PathParam("id") int ownerId, @FormDataParam("uploadedfile") File photo) {
        byte[] bytes = null;
        Image result;
        try {
            bytes = Files.readAllBytes(Paths.get(photo.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = getImageService().findByOwnerId(ownerId);
        if (image == null) {
            result = getImageService().save(new Image(ownerId, bytes));
        } else {
            image.setImage(bytes);
            result = getImageService().update(image);
        }
        return Response.ok(result).build();
    }
}
