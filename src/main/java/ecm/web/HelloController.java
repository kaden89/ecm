package ecm.web;

import ecm.web.dto.ServerURLConfigDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Контроллер для отправки конфигурации URL
 * @author dkarachurin
 */
@Path(value = "")
public class HelloController {
    @GET
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUrlConfig() {
        return Response.ok(new ServerURLConfigDTO()).build();
    }
}
