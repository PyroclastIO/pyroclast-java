package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.responses.BulkProduceEventResponse;
import io.pyroclast.pyroclastjava.v1.topic.responses.ProduceEventResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class BulkProduceEventsResponseDeserializer extends StdDeserializer<BulkProduceEventResponse> {

    public BulkProduceEventsResponseDeserializer() {
        this(null);
    }
    
    public BulkProduceEventsResponseDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public BulkProduceEventResponse deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        if (node.isArray()) {
            List<ProduceEventResponse> responses = new ArrayList<>();
            Iterator<JsonNode> it = node.iterator();
            
            while (it.hasNext()) {
                JsonNode n = it.next();
                responses.add(new ProduceEventResponse(n.get("created").asBoolean()));
            }
            
            return new BulkProduceEventResponse(responses);
        } else {
            String reason = node.get("reason").asText();
            return new BulkProduceEventResponse(reason);
        }
    }
    
}
