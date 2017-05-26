package io.pyroclast.pyroclastjava.v1.topic;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.JsonNode;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = TopicBulkResponseDeserializer.class)
public class TopicBulkResponse  {
    
    public final List<TopicResponse> responses;
    public final boolean success;
    public final String reason;
    
    public TopicBulkResponse(List<TopicResponse> responses) {
        this.responses = responses;
        this.success = true;
        reason = null;
    }
    
    public TopicBulkResponse(String reason) {
        this.responses = null;
        this.success = false;
        this.reason = reason;
    }
    
    public List<TopicResponse> getResponses() {
        return this.responses;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    @Override
    public String toString() {
        return String.format("success: %s, reason %s, responses: %s",
                this.success, this.reason, this.responses);
    }
    
}
