package io.pyroclast.pyroclastjava.v1.topic.responses;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = SubscribeToTopicResponseDeserializer.class)
public class SubscribeToTopicResponse {

    private final boolean success;
    private final String reason;
    
    public SubscribeToTopicResponse(boolean success) {
        this.success = success;
        this.reason = null;
    }
    
    public SubscribeToTopicResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    @Override
    public String toString() {
        return String.format("success: %s, reason %s", this.success, this.reason);
    }
    
}
