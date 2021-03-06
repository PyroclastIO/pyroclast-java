package io.pyroclast.pyroclastjava.v1.topic.deserializers;

import io.pyroclast.pyroclastjava.v1.topic.responses.SubscribeToTopicResult;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class SubscribeToTopicResponseDeserializer extends StdDeserializer<SubscribeToTopicResult> {

    public SubscribeToTopicResponseDeserializer() {
        this(null);
    }

    public SubscribeToTopicResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SubscribeToTopicResult deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        boolean success = false;
        String reason = null;
        
        if (node.has("subscribed")) {
            success = node.get("subscribed").asBoolean();
        }
        
        if (node.has("reason")) {
            reason = node.get("reason").asText();
        }

        return new SubscribeToTopicResult(success, reason);
    }
}
