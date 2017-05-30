package io.pyroclast.pyroclastjava.v1.deployment;

import java.util.List;

public class DeploymentAggregates {
    
    private final List<DeploymentAggregate> aggregates;
    
    public DeploymentAggregates(List<DeploymentAggregate> aggregates) {
        this.aggregates = aggregates;
    }
    
    public List<DeploymentAggregate> getAggregates() {
        return this.aggregates;
    }

}
