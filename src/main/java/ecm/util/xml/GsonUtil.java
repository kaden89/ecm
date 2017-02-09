package ecm.util.xml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import javax.inject.Singleton;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.02.2017.
 */
@Singleton
public class GsonUtil {
    private Gson gson;

    public GsonUtil() {
        //Glassfish can't correctly marshall inner generics, have dto use GSON for it, with LocalDate adapter.
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())).
                registerTypeAdapter(byte[].class, new ByteArrayAdapter()).setExclusionStrategies(new GsonExclusionStrategy()).create();
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
