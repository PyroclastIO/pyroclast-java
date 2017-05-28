package io.pyroclast.pyroclastjava.v1.topic.deserializers;

import io.pyroclast.pyroclastjava.v1.topic.responses.ProducedEventResult;
import io.pyroclast.pyroclastjava.v1.topic.responses.ProducedEventsResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class ProducedEventsResponseDeserializer extends StdDeserializer<ProducedEventsResult> {

    public ProducedEventsResponseDeserializer() {
        this(null);
    }
    
    public ProducedEventsResponseDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public ProducedEventsResult deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        if (node.isArray()) {
            List<ProducedEventResult> responses = new ArrayList<>();
            Iterator<JsonNode> it = node.iterator();
            
            while (it.hasNext()) {
                JsonNode n = it.next();
                responses.add(new ProducedEventResult(n.get("created").asBoolean()));
            }
            
            return new ProducedEventsResult(responses);
        } else {
            String reason = node.get("reason").asText();
            return new ProducedEventsResult(reason);
        }
    }
    
}
