package ecm.web;

import ecm.model.Document;
import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Task;
import ecm.web.to.AbstractDocumentDTO;
import ecm.web.to.TreeNode;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 24.01.2017.
 */
@Path(value = DocumentsRestController.REST_URL)
public class DocumentsRestController extends AbstractRestController{
    static final String REST_URL = "/documents";

    @GET
    @Path("/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRoot(){
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(new TreeNode("Incomings", "incomings", null, true));
        nodes.add(new TreeNode("Outgoings", "outgoings", null, true));
        nodes.add(new TreeNode("Tasks", "tasks", null,true));
        TreeNode<TreeNode> root = new TreeNode<>("Documents", "", nodes);
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeIncomingDocuments(){
        TreeNode<Incoming> root = new TreeNode<>("Incomings", "", incomingDAO.findAll());
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeOutgoingDocuments(){
        TreeNode<Outgoing> root = new TreeNode<>("Outgoings", "", outgoingDAO.findAll());
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTaskDocuments(){
        TreeNode<Task> root = new TreeNode<>("Tasks", "", taskDAO.findAll());
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @PUT
    @Path("/incomings/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, AbstractDocumentDTO dto){
        dto.setId(id);
        Incoming updated = incomingDAO.update((Incoming) documentDTOConverter.fromDTO(dto));
        return Response.ok(documentDTOConverter.toDTO(updated)).build();
    }
}
