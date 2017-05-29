package io.pyroclast.pyroclastjava.v1.service.deserializers;

import io.pyroclast.pyroclastjava.v1.service.ServiceAggregate;
import io.pyroclast.pyroclastjava.v1.service.ServiceAggregates;
import io.pyroclast.pyroclastjava.v1.service.Window;
import io.pyroclast.pyroclastjava.v1.service.responses.ReadAggregatesResult;
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

public class ReadAggregatesResponseDeserializer extends StdDeserializer<ReadAggregatesResult> {

    public ReadAggregatesResponseDeserializer() {
        this(null);
    }

    public ReadAggregatesResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ReadAggregatesResult deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        ObjectMapper mapper = new ObjectMapper();
        List<ServiceAggregate> aggregates = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.getFields();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            JsonNode subnode = field.getValue();

            String aggregateName = field.getKey();
            String id = subnode.get("id").asText();
            String type = subnode.get("type").asText();
            boolean isGrouped = subnode.get("grouped?").asBoolean();
            JsonNode contents = subnode.get("contents");

            if (contents.isArray()) {
                List<Window> parsedWindows = new ArrayList<>();
                Iterator<JsonNode> it = contents.iterator();

                while (it.hasNext()) {
                    JsonNode n = it.next();
                    Window window = mapper.readValue(n.toString(), Window.class);
                    parsedWindows.add(window);
                }

                aggregates.add(new ServiceAggregate(id, type, isGrouped)
                        .withName(aggregateName)
                        .withUngroupedContents(parsedWindows));

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

                aggregates.add(new ServiceAggregate(id, type, isGrouped)
                        .withGroupedContents(parsedWindows));
            }
        }

        ServiceAggregates sa = new ServiceAggregates(aggregates);
        return new ReadAggregatesResult(true, sa);

    }

}
