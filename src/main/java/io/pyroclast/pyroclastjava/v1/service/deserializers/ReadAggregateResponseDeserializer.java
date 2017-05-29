package io.pyroclast.pyroclastjava.v1.service.deserializers;

import io.pyroclast.pyroclastjava.v1.service.ServiceAggregate;
import io.pyroclast.pyroclastjava.v1.service.Window;
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

public class ReadAggregateResponseDeserializer extends StdDeserializer<ReadAggregateResult> {

    public ReadAggregateResponseDeserializer() {
        this(null);
    }

    public ReadAggregateResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ReadAggregateResult deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        ObjectMapper mapper = new ObjectMapper();

        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String type = node.get("type").asText();
        boolean isGrouped = node.get("grouped?").asBoolean();
        JsonNode contents = node.get("contents");

        if (contents.isArray()) {
            List<Window> parsedWindows = new ArrayList<>();
            Iterator<JsonNode> it = contents.iterator();

            while (it.hasNext()) {
                JsonNode n = it.next();
                Window window = mapper.readValue(n.toString(), Window.class);
                parsedWindows.add(window);
            }

            ServiceAggregate sa = new ServiceAggregate(id, type, isGrouped)
                    .withName(name)
                    .withUngroupedContents(parsedWindows);
            return new ReadAggregateResult(true, sa);
        } else {
            Iterator<Map.Entry<String, JsonNode>> fit = contents.getFields();
            Map<String, List<Window>> parsedWindows = new HashMap<>();

            while (fit.hasNext()) {
                List<Window> windows = new ArrayList<>();
                Map.Entry<String, JsonNode> contentField = fit.next();
                String groupName = contentField.getKey();
                JsonNode groupedContents = contentField.getValue();
                Iterator<JsonNode> groupIterator = groupedContents.iterator();

                while (groupIterator.hasNext()) {
                    JsonNode n = groupIterator.next();
                    Window window = mapper.readValue(n.toString(), Window.class);
                    windows.add(window);
                }

                parsedWindows.put(groupName, windows);
            }

            ServiceAggregate sa = new ServiceAggregate(id, type, isGrouped)
                    .withName(name)
                    .withGroupedContents(parsedWindows);
            return new ReadAggregateResult(true, sa);
        }
    }

}
