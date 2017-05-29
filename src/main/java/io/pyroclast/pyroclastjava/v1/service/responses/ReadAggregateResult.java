package io.pyroclast.pyroclastjava.v1.service.responses;

import io.pyroclast.pyroclastjava.v1.service.ServiceAggregate;
import io.pyroclast.pyroclastjava.v1.service.deserializers.ReadAggregateResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ReadAggregateResponseDeserializer.class)
public class ReadAggregateResult {

    private final boolean success;
    private final String reason;
    private final ServiceAggregate aggregate;
    
    public ReadAggregateResult(boolean success, ServiceAggregate aggregate) {
        this.success = success;
        this.aggregate = aggregate;
        this.reason = null;
    }
    
    public ReadAggregateResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.aggregate = null;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getFailureReason() {
        return this.reason;
    }

    public ServiceAggregate getAggregate() {
        return this.aggregate;
    }

}
