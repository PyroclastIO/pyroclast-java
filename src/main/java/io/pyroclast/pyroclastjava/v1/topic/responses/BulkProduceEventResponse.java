package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.responses.ProduceEventResponse;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = BulkProduceEventsResponseDeserializer.class)
public class BulkProduceEventResponse  {
    
    public final List<ProduceEventResponse> responses;
    public final boolean success;
    public final String reason;
    
    public BulkProduceEventResponse(List<ProduceEventResponse> responses) {
        this.responses = responses;
        this.success = true;
        reason = null;
    }
    
    public BulkProduceEventResponse(String reason) {
        this.responses = null;
        this.success = false;
        this.reason = reason;
    }
    
    public List<ProduceEventResponse> getResponses() {
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
