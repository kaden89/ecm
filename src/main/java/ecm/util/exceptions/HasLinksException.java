package ecm.util.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dkarachurin on 18.01.2017.
 */
public class HasLinksException extends WebApplicationException {
    public HasLinksException(String message) {
        super(Response.status(Response.Status.CONFLICT)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
