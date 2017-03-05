package ecm.util.sorting;

import com.google.gson.*;

import java.lang.reflect.Type;


/**
 * Кастомный десериализатор класса {@link Sort}. Маппит проперти DTO на проперти доменных классов
 * @author dkarachurin
 */
public class SortDeserializer implements JsonDeserializer<Sort> {

    private static final String DTO_AUTHOR_NAME = "authorName";
    private static final String AUTHOR_NAME = "author.fullname";
    private static final String DTO_SENDER_NAME = "senderName";
    private static final String SENDER_NAME = "sender.fullname";
    private static final String DTO_RECIPIENT_NAME = "recipientName";
    private static final String RECIPIENT_NAME = "recipient.fullname";
    private static final String DTO_EXECUTOR_NAME = "executorName";
    private static final String EXECUTOR_NAME = "executor.fullname";
    private static final String DTO_CONTROLLER_NAME = "controllerName";
    private static final String CONTROLLER_NAME = "controller.fullname";
    private static final String DTO_POSITION_NAME = "positionName";
    private static final String POSITION_NAME = "position.post";

    @Override
    public Sort deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jobject = (JsonObject) jsonElement;
        String sortFieldName = jobject.get("field").getAsString();
        switch (jobject.get("field").getAsString()) {
            case DTO_AUTHOR_NAME:
                sortFieldName = AUTHOR_NAME;
                break;
            case DTO_SENDER_NAME:
                sortFieldName = SENDER_NAME;
                break;
            case DTO_RECIPIENT_NAME:
                sortFieldName = RECIPIENT_NAME;
                break;
            case DTO_EXECUTOR_NAME:
                sortFieldName = EXECUTOR_NAME;
                break;
            case DTO_CONTROLLER_NAME:
                sortFieldName = CONTROLLER_NAME;
                break;
            case DTO_POSITION_NAME:
                sortFieldName = POSITION_NAME;
                break;
        }
        return new Sort(
                sortFieldName,
                Direction.valueOf(jobject.get("direction").getAsString())
        );
    }
}
