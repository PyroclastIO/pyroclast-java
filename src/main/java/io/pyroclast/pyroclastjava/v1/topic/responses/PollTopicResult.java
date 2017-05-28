package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.deserializers.PollTopicResponseDeserializer;
import io.pyroclast.pyroclastjava.v1.topic.TopicRecord;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = PollTopicResponseDeserializer.class)
public class PollTopicResult {
   
    private final boolean success;
    private final String reason;
    private final List<TopicRecord> records;
    
    public PollTopicResult(boolean success, List<TopicRecord> records) {
        this.success = success;
        this.reason = null;
        this.records = records;
    }
    
    public PollTopicResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.records = null;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getFailureReason() {
        return this.reason;
    }
    
    public List<TopicRecord> getRecords() {
        return this.records;
    }
}
