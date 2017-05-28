package io.pyroclast.pyroclastjava.v1.topic.deserializers;

import io.pyroclast.pyroclastjava.v1.topic.TopicRecord;
import io.pyroclast.pyroclastjava.v1.topic.responses.PollTopicResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class PollTopicResponseDeserializer extends StdDeserializer<PollTopicResult> {

    public PollTopicResponseDeserializer() {
        this(null);
    }

    public PollTopicResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PollTopicResult deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = jp.getCodec().readTree(jp);
        List<TopicRecord> records = new ArrayList<>();

        Iterator<JsonNode> it = node.iterator();
        while (it.hasNext()) {
            JsonNode n = it.next();
            TopicRecord tr = mapper.readValue(n.toString(), TopicRecord.class);
            records.add(tr);
        }

        return new PollTopicResult(true, records);
    }
}
