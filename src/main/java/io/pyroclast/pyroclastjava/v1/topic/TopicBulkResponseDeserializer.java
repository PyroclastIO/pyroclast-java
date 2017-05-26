package io.pyroclast.pyroclastjava.v1.topic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class TopicBulkResponseDeserializer extends StdDeserializer<TopicBulkResponse> {

    public TopicBulkResponseDeserializer() {
        this(null);
    }
    
    public TopicBulkResponseDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public TopicBulkResponse deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        if (node.isArray()) {
            List<TopicResponse> responses = new ArrayList<>();
            Iterator<JsonNode> it = node.iterator();
            
            while (it.hasNext()) {
                JsonNode n = it.next();
                responses.add(new TopicResponse(n.get("created").asBoolean()));
            }
            
            return new TopicBulkResponse(responses);
        } else {
            String reason = node.get("reason").asText();
            return new TopicBulkResponse(reason);
        }
    }
    
}
