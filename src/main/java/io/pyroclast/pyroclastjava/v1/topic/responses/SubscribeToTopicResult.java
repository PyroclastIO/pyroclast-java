package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.deserializers.SubscribeToTopicResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = SubscribeToTopicResponseDeserializer.class)
public class SubscribeToTopicResult {

    private final boolean success;
    private final String reason;
    
    public SubscribeToTopicResult(boolean success) {
        this.success = success;
        this.reason = null;
    }
    
    public SubscribeToTopicResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getFailureReason() {
        return this.reason;
    }
    
    @Override
    public String toString() {
        return String.format("success: %s, reason %s", this.success, this.reason);
    }
    
}
