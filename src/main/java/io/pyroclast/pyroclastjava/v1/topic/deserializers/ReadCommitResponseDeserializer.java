package io.pyroclast.pyroclastjava.v1.topic.deserializers;

import io.pyroclast.pyroclastjava.v1.topic.responses.ReadCommitResult;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class ReadCommitResponseDeserializer extends StdDeserializer<ReadCommitResult> {

    public ReadCommitResponseDeserializer() {
        this(null);
    }

    public ReadCommitResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ReadCommitResult deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        boolean created = node.get("created").asBoolean();

        String reason = null;
        if (node.has("reason")) {
            reason = node.get("reason").asText();
        }

        return new ReadCommitResult(created, reason);
    }
}
