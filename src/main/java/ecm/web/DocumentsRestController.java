package ecm.web;

import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Task;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;
import ecm.web.dto.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dkarachurin on 24.01.2017.
 */
@Path(value = DocumentsRestController.REST_URL)
public class DocumentsRestController extends AbstractRestController {
    static final String REST_URL = "/documents";

    @GET
    @Path("/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRoot() {
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(new TreeNode("Incomings", "incomings", null, true, "incomings"));
        nodes.add(new TreeNode("Outgoings", "outgoings", null, true, "outgoings"));
        nodes.add(new TreeNode("Tasks", "tasks", null, true, "tasks"));
        TreeNode<TreeNode> root = new TreeNode<>("Documents", "", nodes, "");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeIncomingDocuments() {
        List<IncomingDTO> dtos = new ArrayList<>(getIncomingDTOConverter().toDtoCollection(new ArrayList<>(getIncomingService().findAll())));
        TreeNode<IncomingDTO> root = new TreeNode<>("Incomings", "", dtos, "incomings");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeOutgoingDocuments() {
        List<OutgoingDTO> dtos = new ArrayList<>(getOutgoingDTOConverter().toDtoCollection(new ArrayList<>(getOutgoingService().findAll())));
        TreeNode<OutgoingDTO> root = new TreeNode<>("Outgoings", "", dtos, "outgoings");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tree/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTaskDocuments() {
        List<TaskDTO> dtos = new ArrayList<>(getTaskDTOConverter().toDtoCollection(new ArrayList<>(getTaskService().findAll())));
        TreeNode<TaskDTO> root = new TreeNode<>("Tasks", "", dtos, "tasks");
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks(@HeaderParam("Range") RangeHeader range, @QueryParam("sort") Sort sort, @QueryParam("filter") String filterString) {
        if (filterString != null && sort != null && range != null) {
            Filter filter = (Filter) fromJson(filterString, Filter.class);
            Page<Task> page = getTaskService().findAllSortedFilteredAndPageable(sort, filter, range);
            return getPageResponseFromClass(page, Task.class);
        } else if (sort != null && range != null) {
            Page<Task> page = getTaskService().findAllSortedAndPageable(sort, range);
            return getPageResponseFromClass(page, Task.class);
        } else {
            Collection<TaskDTO> tasks = getTaskDTOConverter().toDtoCollection(getTaskService().findAll());
            return Response.ok(toJson(tasks)).header("Content-Range", "items 0-" + tasks.size() + "/" + tasks.size()).build();
        }
    }

    @GET
    @Path("/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOutgoings(@HeaderParam("Range") RangeHeader range, @QueryParam("sort") Sort sort, @QueryParam("filter") String filterString) {
        if (filterString != null && sort != null && range != null) {
            Filter filter = (Filter) fromJson(filterString, Filter.class);
            Page<Outgoing> page = getOutgoingService().findAllSortedFilteredAndPageable(sort, filter, range);
            return getPageResponseFromClass(page, Outgoing.class);
        } else if (sort != null && range != null) {
            Page<Outgoing> page = getOutgoingService().findAllSortedAndPageable(sort, range);
            return getPageResponseFromClass(page, Outgoing.class);
        } else {
            Collection<OutgoingDTO> outgoings = getOutgoingDTOConverter().toDtoCollection(getOutgoingService().findAll());
            return Response.ok(toJson(outgoings)).header("Content-Range", "items 0-" + outgoings.size() + "/" + outgoings.size()).build();
        }
    }

    @GET
    @Path("/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIncomings(@HeaderParam("Range") RangeHeader range, @QueryParam("sort") Sort sort, @QueryParam("filter") String filterString) {
        if (filterString != null && sort != null && range != null) {
            Filter filter = (Filter) fromJson(filterString, Filter.class);
            Page<Incoming> page = getIncomingService().findAllSortedFilteredAndPageable(sort, filter, range);
            return getPageResponseFromClass(page, Incoming.class);
        } else if (sort != null && range != null) {
            Page<Incoming> page = getIncomingService().findAllSortedAndPageable(sort, range);
            return getPageResponseFromClass(page, Incoming.class);
        } else {
            Collection<IncomingDTO> incoming = getIncomingDTOConverter().toDtoCollection(getIncomingService().findAll());
            return Response.ok(toJson(incoming)).header("Content-Range", "items 0-" + incoming.size() + "/" + incoming.size()).build();
        }
    }

    @POST
    @Path("/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(TaskDTO dto) {
        dto.setId(null);
        Task task = getTaskService().save(getTaskDTOConverter().fromDTO(dto));
        return Response.ok(getTaskDTOConverter().toDTO(task)).build();
    }

    @POST
    @Path("/outgoings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOutgoing(OutgoingDTO dto) {
        dto.setId(null);
        Outgoing outgoing = getOutgoingService().save(getOutgoingDTOConverter().fromDTO(dto));
        return Response.ok(getOutgoingDTOConverter().toDTO(outgoing)).build();
    }

    @POST
    @Path("/incomings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createIncoming(IncomingDTO dto) {
        dto.setId(null);
        Incoming incoming = getIncomingService().save(getIncomingDTOConverter().fromDTO(dto));
        return Response.ok(getIncomingDTOConverter().toDTO(incoming)).build();
    }

    @PUT
    @Path("/incomings/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIncoming(@PathParam("id") int id, IncomingDTO dto) {
        dto.setId(id);
        Incoming updated = getIncomingService().update(getIncomingDTOConverter().fromDTO(dto));
        return Response.ok(getIncomingDTOConverter().toDTO(updated)).build();
    }

    @PUT
    @Path("/outgoings/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOutgoing(@PathParam("id") int id, OutgoingDTO dto) {
        dto.setId(id);
        Outgoing updated = getOutgoingService().update(getOutgoingDTOConverter().fromDTO(dto));
        return Response.ok(getOutgoingDTOConverter().toDTO(updated)).build();
    }

    @PUT
    @Path("/tasks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("id") int id, TaskDTO dto) {
        dto.setId(id);
        Task updated = getTaskService().update(getTaskDTOConverter().fromDTO(dto));
        return Response.ok(getTaskDTOConverter().toDTO(updated)).build();
    }

    @DELETE
    @Path("/tasks/{id}")
    public Response deleteTask(@PathParam("id") int taskId) {
        getTaskService().delete(taskId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/incomings/{id}")
    public Response deleteIncoming(@PathParam("id") int incomingId) {
        getIncomingService().delete(incomingId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/outgoings/{id}")
    public Response deleteOutgoing(@PathParam("id") int outgoingId) {
        getOutgoingService().delete(outgoingId);
        return Response.ok().build();
    }
}
