package ecm.web;

import ecm.web.dto.ServerURLConfigDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dkarachurin on 27.02.2017.
 */
@Path(value = "")
public class HelloController extends AbstractRestController {
    @GET
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRoot() {
        return Response.ok(new ServerURLConfigDTO()).build();
    }
}
