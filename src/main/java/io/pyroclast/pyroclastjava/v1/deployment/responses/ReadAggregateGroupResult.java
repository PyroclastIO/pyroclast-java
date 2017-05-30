package io.pyroclast.pyroclastjava.v1.deployment.responses;

import io.pyroclast.pyroclastjava.v1.deployment.DeploymentAggregate;
import io.pyroclast.pyroclastjava.v1.deployment.deserializers.ReadAggregateGroupResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ReadAggregateGroupResponseDeserializer.class)
public class ReadAggregateGroupResult {

    private final boolean success;
    private final String reason;
    private final DeploymentAggregate aggregate;

    public ReadAggregateGroupResult(boolean success, DeploymentAggregate aggregate) {
        this.success = success;
        this.aggregate = aggregate;
        this.reason = null;
    }

    public ReadAggregateGroupResult(boolean success, String reason) {
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
