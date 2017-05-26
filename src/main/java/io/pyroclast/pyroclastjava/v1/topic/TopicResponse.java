package io.pyroclast.pyroclastjava.v1.topic;

import java.lang.reflect.Type;

public class TopicResponse {
    
    public final boolean created;
    public final String reason;
    
    public TopicResponse(boolean created) {
        this.created = created;
        this.reason = null;
    }
    
    public TopicResponse(boolean created, String reason) {
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
