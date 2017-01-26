
package ecm.web;

import com.google.gson.*;
import ecm.dao.GenericDAO;
import ecm.dao.ImageDaoJPA;
import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Person;
import ecm.model.Task;
import ecm.util.xml.ByteArrayAdapter;
import ecm.util.xml.LocalDateAdapter;

import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by dkarachurin on 24.01.2017.
 */
public class AbstractRestController {

    //Glassfish can't correctly marshall generics, have to use GSON for it, with LocalDate adapter.
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())).
            registerTypeAdapter(byte[].class, new ByteArrayAdapter()).create();

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


    String toJson(Object obj){
        return gson.toJson(obj);
    }
}
