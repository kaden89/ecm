package ecm.util.xml;

import com.google.gson.*;
import ecm.model.Document;

import java.lang.reflect.Type;

/**
 * Created by dkarachurin on 31.01.2017.
 */
public class DocumentElementAdapter implements JsonSerializer<Document> {
    @Override
    public JsonElement serialize(Document document, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(document.getClass().getSimpleName()));
        return result;
    }
}
