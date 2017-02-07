package ecm.web;

import ecm.model.Document;
import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Task;
import ecm.web.dto.AbstractDocumentDTO;
import ecm.web.dto.IncomingDTO;
import ecm.web.dto.TreeNode;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dkarachurin on 24.01.2017.
 */
@Path(value = DocumentsRestController.REST_URL)
public class DocumentsRestController extends AbstractRestController{
    static final String REST_URL = "/documents";
    //TODO move @GET's here and make openDocTab universally
    @GET
    @Path("/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRoot(){
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(new TreeNode("Incomings", "incomings", null, true, "incomings"));
        nodes.add(new TreeNode("Outgoings", "outgoings", null, true, "outgoings"));
        nodes.add(new TreeNode("Tasks", "tasks", null,true, "tasks"));
        TreeNode<TreeNode> root = new TreeNode<>("Documents", "", nodes, "");
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeIncomingDocuments(){
        List<AbstractDocumentDTO> dtos = new ArrayList<>(documentDTOConverter.toDtoCollection(new ArrayList<>(incomingDAO.findAll())));
        TreeNode<AbstractDocumentDTO> root = new TreeNode<>("Incomings", "", dtos, "incomings");
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeOutgoingDocuments(){
        List<AbstractDocumentDTO> dtos = new ArrayList<>(documentDTOConverter.toDtoCollection(new ArrayList<>(outgoingDAO.findAll())));
        TreeNode<AbstractDocumentDTO> root = new TreeNode<>("Outgoings", "", dtos, "outgoings");
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTaskDocuments(){
        List<AbstractDocumentDTO> dtos = new ArrayList<>(documentDTOConverter.toDtoCollection(new ArrayList<>(taskDAO.findAll())));
        TreeNode<AbstractDocumentDTO> root = new TreeNode<>("Tasks", "", dtos, "tasks");
        String jsonInString = gson.toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks(){
        GenericEntity<List<AbstractDocumentDTO>> tasks = new GenericEntity<List<AbstractDocumentDTO>>(new ArrayList<>(documentDTOConverter.toDtoCollection(new ArrayList<>(taskDAO.findAll())))) {
        };
        int size = tasks.getEntity().size();
        return Response.ok(tasks).header( "Content-Range" , "items 0-"+size+"/"+size).build();
    }
    @GET
    @Path("/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOutgoings(){
        GenericEntity<List<AbstractDocumentDTO>> tasks = new GenericEntity<List<AbstractDocumentDTO>>(new ArrayList<>(documentDTOConverter.toDtoCollection(new ArrayList<>(outgoingDAO.findAll())))) {
        };
        int size = tasks.getEntity().size();
        return Response.ok(tasks).header( "Content-Range" , "items 0-"+size+"/"+size).build();
    }
    @GET
    @Path("/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIncomings(){
        GenericEntity<List<AbstractDocumentDTO>> tasks = new GenericEntity<List<AbstractDocumentDTO>>(new ArrayList<>(documentDTOConverter.toDtoCollection(new ArrayList<>(incomingDAO.findAll())))) {
        };
        int size = tasks.getEntity().size();
        return Response.ok(tasks).header( "Content-Range" , "items 0-"+size+"/"+size).build();
    }

    @PUT
    @Path("/incomings/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIncoming(@PathParam("id") int id, AbstractDocumentDTO dto){
        dto.setId(id);
        Incoming updated = incomingDAO.update((Incoming) documentDTOConverter.fromDTO(dto));
        return Response.ok(documentDTOConverter.toDTO(updated)).build();
    }

    @PUT
    @Path("/outgoings/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOutgoing(@PathParam("id") int id, AbstractDocumentDTO dto){
        dto.setId(id);
        Outgoing updated = outgoingDAO.update((Outgoing) documentDTOConverter.fromDTO(dto));
        return Response.ok(documentDTOConverter.toDTO(updated)).build();
    }

    @PUT
    @Path("/tasks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("id") int id, AbstractDocumentDTO dto){
        dto.setId(id);
        Task updated = taskDAO.update((Task) documentDTOConverter.fromDTO(dto));
        return Response.ok(documentDTOConverter.toDTO(updated)).build();
    }

    @DELETE
    @Path("/tasks/{id}")
    public Response deleteTask(@PathParam("id") int taskId){
        //log.info("delete organization with id "+organizationId);
        taskDAO.delete(taskId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/incomings/{id}")
    public Response deleteIncoming(@PathParam("id") int incomingId){
        //log.info("delete organization with id "+organizationId);
        incomingDAO.delete(incomingId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/outgoings/{id}")
    public Response deleteOutgoing(@PathParam("id") int outgoingId){
        //log.info("delete organization with id "+organizationId);
        outgoingDAO.delete(outgoingId);
        return Response.ok().build();
    }
}
