package io.pyroclast.pyroclastjava.v1.service.deserializers;

import io.pyroclast.pyroclastjava.v1.service.ServiceAggregate;
import io.pyroclast.pyroclastjava.v1.service.Window;
import io.pyroclast.pyroclastjava.v1.service.responses.ReadAggregateGroupResult;
import io.pyroclast.pyroclastjava.v1.service.responses.ReadAggregateResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class ReadAggregateGroupResponseDeserializer extends StdDeserializer<ReadAggregateGroupResult> {

    public ReadAggregateGroupResponseDeserializer() {
        this(null);
    }

    public ReadAggregateGroupResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ReadAggregateGroupResult deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        ObjectMapper mapper = new ObjectMapper();

        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String type = node.get("type").asText();      
        String group = node.get("group").asText();
        boolean isGrouped = node.get("grouped?").asBoolean();
        JsonNode contents = node.get("contents");

        List<Window> parsedWindows = new ArrayList<>();
        Iterator<JsonNode> it = contents.iterator();

        while (it.hasNext()) {
            JsonNode n = it.next();
            Window window = mapper.readValue(n.toString(), Window.class);
            parsedWindows.add(window);
        }
        
        Map<String, List<Window>> result = new HashMap<>();
        result.put(group, parsedWindows);

        ServiceAggregate sa = new ServiceAggregate(id, type, isGrouped)
                .withName(name)
                .withGroupedContents(result);
        return new ReadAggregateGroupResult(true, sa);
    }
}
