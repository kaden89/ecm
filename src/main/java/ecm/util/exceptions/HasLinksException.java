package ecm.util.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Исключение выбрасываемое если документы в БД содержат ссылки на удаляемый класс {@link ecm.model.Person}
 * @author dkarachurin
 */
public class HasLinksException extends WebApplicationException {
    public HasLinksException(String message) {
        super(Response.status(Response.Status.CONFLICT)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
