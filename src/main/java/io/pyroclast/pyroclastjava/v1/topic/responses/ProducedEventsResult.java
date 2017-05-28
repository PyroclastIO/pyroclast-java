package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.deserializers.ProducedEventsResponseDeserializer;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ProducedEventsResponseDeserializer.class)
public class ProducedEventsResult  {
    
    public final List<ProducedEventResult> responses;
    public final boolean success;
    public final String reason;
    
    public ProducedEventsResult(List<ProducedEventResult> responses) {
        this.responses = responses;
        this.success = true;
        reason = null;
    }
    
    public ProducedEventsResult(String reason) {
        this.responses = null;
        this.success = false;
        this.reason = reason;
    }
    
    public List<ProducedEventResult> getResponses() {
        return this.responses;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getFailureReason() {
        return this.reason;
    }
}
