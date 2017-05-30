package io.pyroclast.pyroclastjava.v1.deployment.responses;

import io.pyroclast.pyroclastjava.v1.deployment.DeploymentAggregates;
import io.pyroclast.pyroclastjava.v1.deployment.deserializers.ReadAggregatesResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ReadAggregatesResponseDeserializer.class)
public class ReadAggregatesResult {
    
    private final boolean success;
    private final String reason;
    private final DeploymentAggregates aggregates;
    
    public ReadAggregatesResult(boolean success, DeploymentAggregates aggregates) {
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
    
    public DeploymentAggregates getAggregates() {
        return this.aggregates;
    }

}
