package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.TopicRecord;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = PollTopicResponseDeserializer.class)
public class PollTopicResponse {
   
    private final boolean success;
    private final String reason;
    private final List<TopicRecord> records;
    
    public PollTopicResponse(boolean success, List<TopicRecord> records) {
        this.success = success;
        this.reason = null;
        this.records = records;
    }
    
    public PollTopicResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.records = null;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public List<TopicRecord> getRecords() {
        return this.records;
    }
    
    @Override
    public String toString() {
        return String.format("success: %s, reason %s", this.success, this.reason);
    }
}
