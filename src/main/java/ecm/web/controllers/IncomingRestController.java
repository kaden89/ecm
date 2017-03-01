package ecm.web.controllers;

import ecm.model.Incoming;
import ecm.web.dto.IncomingDTO;

import javax.ws.rs.Path;

/**
 * @author dkarachurin on 01.03.2017.
 */
@Path(value = IncomingRestController.REST_URL)
public class IncomingRestController extends AbstractGenericRestController<Incoming, IncomingDTO> {
    static final String REST_URL = "/incomings";
}