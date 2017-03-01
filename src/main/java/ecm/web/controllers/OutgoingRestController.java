package ecm.web.controllers;

import ecm.model.Outgoing;
import ecm.web.dto.OutgoingDTO;

import javax.ws.rs.Path;

/**
 * @author dkarachurin on 01.03.2017.
 */
@Path(value = OutgoingRestController.REST_URL)
public class OutgoingRestController extends AbstractGenericRestController<Outgoing, OutgoingDTO> {
    public static final String REST_URL = "/outgoings";
}
