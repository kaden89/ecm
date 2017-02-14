package ecm.web;

import ecm.web.dto.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by dkarachurin on 23.01.2017.
 */
@Path(value = WidgetRestController.REST_URL)
public class WidgetRestController extends AbstractRestController {
    static final String REST_URL = "/widgets";
    private ClassLoader classLoader = getClass().getClassLoader();

    @GET
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons() {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/grid.html"));
        response.setScript(readFile("/js/templates/personGrid.js"));
        return Response.ok(response).build();
    }

    @GET
    @Path("/persons/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewEmployeeTemplate() {
        StaffWidgetResponse response = new StaffWidgetResponse();
        response.setTemplate(readFile("/html/person.html"));
        response.setScript(readFile("/js/templates/person.js"));
        response.setEntity(new PersonDTO());
        return Response.ok(response).build();
    }

    @GET
    @Path("/persons/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTemplate(@PathParam("id") int employeeId) {
        StaffWidgetResponse response = new StaffWidgetResponse();
        response.setTemplate(readFile("/html/person.html"));
        response.setEntity(getPersonDTOConverter().toDTO(getPersonService().find(employeeId)));
        response.setScript(readFile("/js/templates/person.js"));

        return Response.ok(response).build();
    }

    @GET
    @Path("/incomings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIncomings() {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/grid.html"));
        response.setScript(readFile("/js/templates/incomingGrid.js"));
        return Response.ok(response).build();
    }

    @GET
    @Path("/incomings/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewIncomingTemplate() {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/incoming.html"));
        response.setScript(readFile("/js/templates/incoming.js"));
        response.setEntity(new IncomingDTO());
        return Response.ok(response).build();
    }

    @GET
    @Path("/incomings/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIncomingTemplate(@PathParam("id") int incomingId) {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/incoming.html"));
        response.setEntity(getIncomingDTOConverter().toDTO(getIncomingService().find(incomingId)));
        response.setScript(readFile("/js/templates/incoming.js"));

        return Response.ok(response).build();
    }

    @GET
    @Path("/outgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOutgoings() {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/grid.html"));
        response.setScript(readFile("/js/templates/outgoingGrid.js"));
        return Response.ok(response).build();
    }

    @GET
    @Path("/outgoings/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewOutgoingTemplate() {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/outgoing.html"));
        response.setScript(readFile("/js/templates/outgoing.js"));
        response.setEntity(new OutgoingDTO());
        return Response.ok(response).build();
    }

    @GET
    @Path("/outgoings/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOutgoingTemplate(@PathParam("id") int outgoingId) {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/outgoing.html"));
        response.setEntity(getOutgoingDTOConverter().toDTO(getOutgoingService().find(outgoingId)));
        response.setScript(readFile("/js/templates/outgoing.js"));

        return Response.ok(response).build();
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskGrid() {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/grid.html"));
        response.setScript(readFile("/js/templates/taskGrid.js"));
        return Response.ok(response).build();
    }

    @GET
    @Path("/tasks/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewTaskTemplate() {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/task.html"));
        response.setScript(readFile("/js/templates/task.js"));
        response.setEntity(new TaskDTO());
        return Response.ok(response).build();
    }

    @GET
    @Path("/tasks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskTemplate(@PathParam("id") int taskId) {
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/task.html"));
        response.setEntity(getTaskDTOConverter().toDTO(getTaskService().find(taskId)));
        response.setScript(readFile("/js/templates/task.js"));
        return Response.ok(response).build();
    }

    private String readFile(String fileName) {
        StringBuilder result = new StringBuilder("");
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
