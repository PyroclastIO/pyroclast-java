package io.pyroclast.pyroclastjava.v1.service;

import java.util.List;
import java.util.Map;

public class ServiceAggregate {

    private final String id;
    private final String type;
    private final boolean isGrouped;
    private String name;
    private List<Window> contents;
    private Map<String, List<Window>> groupedContents;
    
    public ServiceAggregate(String id, String type, boolean isGrouped) {
        this.id = id;
        this.type = type;
        this.isGrouped = isGrouped;
    }
    
    public ServiceAggregate withName(String name) {
        this.name = name;
        return this;
    }
    
    public ServiceAggregate withUngroupedContents(List<Window> contents) {
        this.contents = contents;
        return this;
    }
    
    public ServiceAggregate withGroupedContents(Map<String, List<Window>> contents) {
        this.groupedContents = contents;
        return this;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }
    
    public boolean isGrouped() {
        return this.isGrouped;
    }
    
    public List<Window> getContents() throws IllegalAccessException {
        if (!this.isGrouped()) {
            return this.contents;
        } else {
            throw new IllegalAccessException("This aggregate is grouped. Call getGroupedContents instead.");
        }
    }
    
    public Map<String, List<Window>> getGroupedContents() throws IllegalAccessException {
        if (this.isGrouped()) {
            return this.groupedContents;
        } else {
            throw new IllegalAccessException("This aggregate isn't grouped. Call getContents instead.");
        }
    }
    
}
