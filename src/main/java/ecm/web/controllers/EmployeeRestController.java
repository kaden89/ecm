package ecm.web.controllers;

import ecm.model.Image;
import ecm.model.Person;
import ecm.model.Post;
import ecm.service.PostService;
import ecm.web.dto.PersonDTO;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dkarachurin on 01.03.2017.
 */
@Path(value = EmployeeRestController.REST_URL)
public class EmployeeRestController extends AbstractGenericRestController<Person, PersonDTO> {
    public static final String REST_URL = "/employees";

    @EJB
    private PostService postService;

    @GET
    @Path("/posts")
    public Response getPosts() {
        GenericEntity<List<Post>> posts = new GenericEntity<List<Post>>(new ArrayList<>(postService.findAll())) {
        };
        int size = posts.getEntity().size();
        return Response.ok(posts).header("Content-Range", "items 0-" + size + "/" + size).build();
    }

    @GET
    @Path("/posts/{id}")
    public Response getPosts(@PathParam("id") int postId) {
        return Response.ok(postService.find(postId)).build();
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
