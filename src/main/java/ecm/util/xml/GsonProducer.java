package ecm.util.xml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import ecm.util.sorting.Sort;
import ecm.util.sorting.SortDeserializer;

import javax.enterprise.inject.Produces;
import java.time.LocalDate;


/**
 * Производит экземпляр класса {@link Gson} для CDI
 * @author dkarachurin
 */
public class GsonProducer {
    @Produces
    public Gson produceGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString()))
                .registerTypeAdapter(Sort.class, new SortDeserializer())
                .create();
    }
}
