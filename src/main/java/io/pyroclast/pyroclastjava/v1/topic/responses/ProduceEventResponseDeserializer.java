package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.responses.ProduceEventResponse;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;


public class ProduceEventResponseDeserializer extends StdDeserializer<ProduceEventResponse> {
    
    public ProduceEventResponseDeserializer() {
        this(null);
    }
    
    public ProduceEventResponseDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public ProduceEventResponse deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        boolean created = node.get("created").asBoolean();
       
        String reason = null;
        if (node.has("reason")) {
            reason = node.get("reason").asText();
        }
        
        return new ProduceEventResponse(created, reason);
    }
    
}
