package io.pyroclast.pyroclastjava.v1.service.responses;

import io.pyroclast.pyroclastjava.v1.service.ServiceAggregates;
import io.pyroclast.pyroclastjava.v1.service.deserializers.ReadAggregatesResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ReadAggregatesResponseDeserializer.class)
public class ReadAggregatesResult {
    
    private final boolean success;
    private final String reason;
    private final ServiceAggregates aggregates;
    
    public ReadAggregatesResult(boolean success, ServiceAggregates aggregates) {
        this.success = success;
        this.aggregates = aggregates;
        this.reason = null;
    }
    
    public ReadAggregatesResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.aggregates = null;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getFailureReason() {
        return this.reason;
    }
    
    public ServiceAggregates getAggregates() {
        return this.aggregates;
    }

}
