package ecm.web;

import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Person;
import ecm.web.dto.DocumentWidgetResponse;
import ecm.web.dto.StaffWidgetResponse;

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
public class WidgetRestController extends AbstractRestController{
    static final String REST_URL = "/widgets";
    private ClassLoader classLoader = getClass().getClassLoader();

    @GET
    @Path("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewEmployeeTemplate(){
        StaffWidgetResponse response = new StaffWidgetResponse();
        response.setTemplate(readFile("/html/person.html"));
        response.setScript(readFile("/js/person.js"));
        response.setEntity(staffDTOConverter.toDTO(new Person()));
        return Response.ok(response).build();
    }

    @GET
    @Path("/person/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTemplate(@PathParam("id") int employeeId){
        StaffWidgetResponse response = new StaffWidgetResponse();
        response.setTemplate(readFile("/html/person.html"));
        response.setEntity(staffDTOConverter.toDTO(personDAO.find(employeeId)));
        response.setScript(readFile("/js/person.js"));

        return Response.ok(response).build();
    }
    @GET
    @Path("/incoming")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewIncomingTemplate(){
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/incoming.html"));
        response.setScript(readFile("/js/incoming.js"));
        response.setEntity(documentDTOConverter.toDTO(new Incoming()));
        return Response.ok(response).build();
    }

    @GET
    @Path("/incoming/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIncomingTemplate(@PathParam("id") int incomingId){
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/incoming.html"));
        response.setEntity(documentDTOConverter.toDTO(incomingDAO.find(incomingId)));
        response.setScript(readFile("/js/incoming.js"));

        return Response.ok(response).build();
    }

    @GET
    @Path("/outgoing")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewOutgoingTemplate(){
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/outgoing.html"));
        response.setScript(readFile("/js/outgoing.js"));
        response.setEntity(documentDTOConverter.toDTO(new Outgoing()));
        return Response.ok(response).build();
    }

    @GET
    @Path("/outgoing/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOutgoingTemplate(@PathParam("id") int outgoingId){
        DocumentWidgetResponse response = new DocumentWidgetResponse();
        response.setTemplate(readFile("/html/outgoing.html"));
        response.setEntity(documentDTOConverter.toDTO(outgoingDAO.find(outgoingId)));
        response.setScript(readFile("/js/outgoing.js"));

        return Response.ok(response).build();
    }

    private String readFile(String fileName){
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
