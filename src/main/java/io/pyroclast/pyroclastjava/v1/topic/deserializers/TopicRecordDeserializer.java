package io.pyroclast.pyroclastjava.v1.topic.deserializers;

import io.pyroclast.pyroclastjava.v1.topic.TopicRecord;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class TopicRecordDeserializer extends StdDeserializer<TopicRecord> {
    
   public TopicRecordDeserializer() {
        this(null);
    }

    public TopicRecordDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TopicRecord deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        String topic = node.get("topic").asText();
        long partition = node.get("partition").asLong();
        long offset = node.get("offset").asLong();
        long timestamp = node.get("timestamp").asLong();
        
        String key = null;
        if (node.has("key")) {
            key = node.get("key").asText();
        }
        
        Map<Object, Object> value = new ObjectMapper().readValue(node.get("value").toString(), Map.class);
        return new TopicRecord(topic, key, partition, offset, timestamp, value);
    }

}
