package io.pyroclast.pyroclastjava.v1.service;

import java.util.List;

public class ServiceAggregates {
    
    private final List<ServiceAggregate> aggregates;
    
    public ServiceAggregates(List<ServiceAggregate> aggregates) {
        this.aggregates = aggregates;
    }
    
    public List<ServiceAggregate> getAggregates() {
        return this.aggregates;
    }

}
