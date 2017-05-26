package io.pyroclast.pyroclastjava.v1.topic;

import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;


public class TopicResponseDeserializer extends StdDeserializer<TopicResponse> {
    
    public TopicResponseDeserializer() {
        this(null);
    }
    
    public TopicResponseDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public TopicResponse deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        boolean created = node.get("created").asBoolean();
        String reason = node.get("reason").asText();
        
        return new TopicResponse(created, reason);
    }
    
}
