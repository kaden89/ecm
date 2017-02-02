
package ecm.web;

import com.google.gson.*;
import ecm.dao.GenericDAO;
import ecm.dao.ImageDaoJPA;
import ecm.model.*;
import ecm.util.xml.ByteArrayAdapter;
import ecm.util.xml.GsonExclusionStrategy;
import ecm.web.dto.DocumentDTOConverter;

import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 24.01.2017.
 */
public class AbstractRestController {
    //Glassfish can't correctly marshall generics, have dto use GSON for it, with LocalDate adapter.
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())).
            registerTypeAdapter(byte[].class, new ByteArrayAdapter()).setExclusionStrategies(new GsonExclusionStrategy()).create();

    @Inject
    GenericDAO<Person> personDAO;

    @Inject
    GenericDAO<Outgoing> outgoingDAO;

    @Inject
    GenericDAO<Incoming> incomingDAO;

    @Inject
    GenericDAO<Task> taskDAO;

    @Inject
    ImageDaoJPA imageDAO;

    @Inject
    DocumentDTOConverter documentDTOConverter;


    String toJson(Object obj){
        return gson.toJson(obj);
    }
}
