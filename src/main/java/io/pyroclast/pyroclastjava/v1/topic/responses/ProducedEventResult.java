package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.deserializers.ProduceEventResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ProduceEventResponseDeserializer.class)
public class ProducedEventResult {
    
    public final boolean success;
    public final String reason;
    
    public ProducedEventResult(boolean success) {
        this.success = success;
        this.reason = null;
    }
    
    public ProducedEventResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getFailureReason() {
        return this.reason;
    }

}
