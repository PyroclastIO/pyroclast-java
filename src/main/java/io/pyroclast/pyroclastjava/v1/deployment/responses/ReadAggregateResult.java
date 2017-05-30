package io.pyroclast.pyroclastjava.v1.deployment.responses;

import io.pyroclast.pyroclastjava.v1.deployment.DeploymentAggregate;
import io.pyroclast.pyroclastjava.v1.deployment.deserializers.ReadAggregateResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ReadAggregateResponseDeserializer.class)
public class ReadAggregateResult {

    private final boolean success;
    private final String reason;
    private final DeploymentAggregate aggregate;
    
    public ReadAggregateResult(boolean success, DeploymentAggregate aggregate) {
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

    public DeploymentAggregate getAggregate() {
        return this.aggregate;
    }

}
