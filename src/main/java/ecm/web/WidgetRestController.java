package ecm.web;

import com.google.gson.Gson;
import ecm.dao.GenericDAO;
import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Person;
import ecm.model.Task;
import ecm.util.WidgetResponse;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by dkarachurin on 23.01.2017.
 */
@Path(value = WidgetRestController.REST_URL)
public class WidgetRestController {
    static final String REST_URL = "/widgets";
    private ClassLoader classLoader = getClass().getClassLoader();
    //Glassfish can't correctly marshall generics, have to use GSON for it.
    private Gson gson = new Gson();

    @Inject
    private GenericDAO<Person> personDAO;

    @Inject
    private GenericDAO<Outgoing> outgoingDAO;

    @Inject
    private GenericDAO<Incoming> incomingDAO;

    @Inject
    private GenericDAO<Task> taskDAO;

    @GET
    @Path("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewEmployeeTemplate(){
        WidgetResponse response = new WidgetResponse();
        response.setTemplate(readFile("/html/person.html"));
        return Response.ok(response).build();
    }

    @GET
    @Path("/person/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTemplate(@PathParam("id") int employeeId){
        WidgetResponse response = new WidgetResponse<Person>();
        response.setTemplate(readFile("/html/person.html"));
        response.setModel(personDAO.find(employeeId));
        String jsonInString = gson.toJson(response);

        return Response.ok(jsonInString).build();
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
