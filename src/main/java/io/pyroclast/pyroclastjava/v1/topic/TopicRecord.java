package io.pyroclast.pyroclastjava.v1.topic;

import java.util.Map;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = TopicRecordDeserializer.class)
public class TopicRecord {
    
    private final String topic;
    private final String key;
    private final long partition;
    private final long offset;
    private final long timestamp;
    private final Map<Object, Object> value;
    
    public TopicRecord(String topic, String key, long partition, long offset, long timestamp, Map<Object, Object> value) {
        this.topic = topic;
        this.key = key;
        this.partition = partition;
        this.offset = offset;
        this.timestamp = timestamp;
        this.value = value;
    }
    
    public String getTopic() {
        return this.topic;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public long getPartition() {
        return this.partition;
    }
    
    public long getOffset() {
        return this.offset;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public Map<Object, Object> getValue() {
        return this.value;
    }

}
