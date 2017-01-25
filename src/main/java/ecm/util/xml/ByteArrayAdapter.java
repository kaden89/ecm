package ecm.util.xml;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


import java.lang.reflect.Type;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by dkarachurin on 25.01.2017.
 */
public class ByteArrayAdapter implements JsonSerializer<byte[]> {
    @Override
    public JsonElement serialize(byte[] bytes, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(Base64.getEncoder().encodeToString(bytes));
    }
}
