package io.pyroclast.pyroclastjava.v1.service;

import io.pyroclast.pyroclastjava.v1.service.deserializers.WindowDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = WindowDeserializer.class)
public class Window {
    
    private final double value;
    private long lowerBound;
    private long upperBound;
    
    public Window(double value) {
        this.value = value;
    }
    
    public Window withLowerBound(long lowerBound) {
        this.lowerBound = lowerBound;
        return this;
    }
    
    public Window withUpperBound(long upperBound) {
        this.upperBound = upperBound;
        return this;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public long getLowerBound() {
        return this.lowerBound;
    }
    
    public long getUpperBound() {
        return this.upperBound;
    }
}
