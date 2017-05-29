package io.pyroclast.pyroclastjava.v1.service.deserializers;

import io.pyroclast.pyroclastjava.v1.service.Window;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class WindowDeserializer extends StdDeserializer<Window> {

    public WindowDeserializer() {
        this(null);
    }

    public WindowDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Window deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        Double value = node.get("value").asDouble();
        Window window = new Window(value);
        
        if (node.has("bounds")) {
            long lowerBound = node.get("bounds").get(0).asLong();
            long upperBound = node.get("bounds").get(1).asLong();
            
            window.withLowerBound(lowerBound).withUpperBound(upperBound);
        }

        return window;
    }

}
