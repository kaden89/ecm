package ecm.util.xml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.time.LocalDate;


/**
 * Created by dkarachurin on 13.02.2017.
 */
public class GsonProducer {
    @Produces
    public Gson produceGson(InjectionPoint injectionPoint) {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())).
                registerTypeAdapter(byte[].class, new ByteArrayAdapter()).create();
    }
}
