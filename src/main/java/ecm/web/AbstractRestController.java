package ecm.web;

import com.google.gson.Gson;
import ecm.dao.GenericDAO;
import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Person;
import ecm.model.Task;

import javax.inject.Inject;

/**
 * Created by dkarachurin on 24.01.2017.
 */
public class AbstractRestController {

    //Glassfish can't correctly marshall generics, have to use GSON for it.
    Gson gson = new Gson();

    @Inject
    GenericDAO<Person> personDAO;

    @Inject
    GenericDAO<Outgoing> outgoingDAO;

    @Inject
    GenericDAO<Incoming> incomingDAO;

    @Inject
    GenericDAO<Task> taskDAO;
}
