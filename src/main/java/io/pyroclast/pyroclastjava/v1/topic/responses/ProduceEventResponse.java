package io.pyroclast.pyroclastjava.v1.topic.responses;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ProduceEventResponseDeserializer.class)
public class ProduceEventResponse {
    
    public final boolean created;
    public final String reason;
    
    public ProduceEventResponse(boolean created) {
        this.created = created;
        this.reason = null;
    }
    
    public ProduceEventResponse(boolean created, String reason) {
        this.created = created;
        this.reason = reason;
    }
    
    public boolean getCreated() {
        return this.created;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    @Override
    public String toString() {
        return String.format("created: %s, reason %s", this.created, this.reason);
    }

}
