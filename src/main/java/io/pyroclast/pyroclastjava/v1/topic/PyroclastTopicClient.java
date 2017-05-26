package io.pyroclast.pyroclastjava.v1.topic;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

public class PyroclastTopicClient {
    
    private static final String VERSION;
    private static final String DEFAULT_REGION;
    private static final ObjectMapper MAPPER;
    
    private String topicId;
    private String writeApiKey;
    private String endpoint;
    private String region;
    private String format;
    private boolean validated;
    
    static {
        VERSION = "v1";
        DEFAULT_REGION = "us-east-1";
        MAPPER = new ObjectMapper();
    }
    
    public PyroclastTopicClient() {
        this.format = "application/json";
        this.region = "us-east-1";
        this.validated = false;
    }
    
    public PyroclastTopicClient withTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }
    
    public PyroclastTopicClient withWriteApiKey(String writeApiKey) {
        this.writeApiKey = writeApiKey;
        return this;
    }
    
    public PyroclastTopicClient withFormat(String format) {
        this.format = format;
        return this;
    }
    
    public PyroclastTopicClient withRegion(String region) {
        this.region = region;
        return this;
    }
    
    public PyroclastTopicClient withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }
    
    public PyroclastTopicClient buildClient() {
        if (this.topicId == null) {
            throw new IllegalArgumentException("Topic ID must be configured.");
        }
        
        if (this.writeApiKey == null) {
            throw new IllegalArgumentException("Write API Key must be configured.");
        }
        
        this.validated = true;
        return this;
    }
    
    private String buildEndpoint() {
        if (this.endpoint != null) {
            return String.format("%s/api/%s", this.endpoint, VERSION);
        } else if (this.region != null) {
            return String.format("https://topic.%s.pyroclast.io/api/%s", this.region, VERSION);
        } else {
            return String.format("https://topic.%s.pyroclast.io/api/%s", DEFAULT_REGION, VERSION);
        }
    }
    
    private TopicResponse processResponse(CloseableHttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        
        switch(status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                return MAPPER.readValue(json, TopicResponse.class);
            case 400:
                return new TopicResponse(false, "Event data was malformed.");
                
            case 401:
                return new TopicResponse(false, "API key unauthorized to perform this action.");
            
            default:
                return new TopicResponse(false, response.getStatusLine().toString());
        }
    }
    
    private TopicBulkResponse processBulkResponse(CloseableHttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        
        switch(status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();
                
                return MAPPER.readValue(json, TopicBulkResponse.class);
            case 400:
                return new TopicBulkResponse("Event data was malformed.");
                
            case 401:
                return new TopicBulkResponse("API key unauthorized to perform this action.");
            
            default:
                return new TopicBulkResponse(response.getStatusLine().toString());
        }
    }
    
    public TopicResponse produceEvent(Map<Object, Object> event) throws IOException {
        if (!this.validated) {
            throw new IllegalArgumentException("Must call buildClient before executing methods.");
        }
        
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String url = String.format("%s/topic/%s/produce",
                    this.buildEndpoint(), this.topicId);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.writeApiKey);
            httpPost.addHeader("Content-type", this.format);
            
            String jsonString = MAPPER.writeValueAsString(event);
            HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
            httpPost.setEntity(entity);
            
            CloseableHttpResponse response;
            
            response = httpclient.execute(httpPost);
            TopicResponse tr = processResponse(response);
            response.close();
            
            return tr;
        }
    }
        
    public TopicBulkResponse produceEvents(List<Map<Object, Object>> events) throws IOException {
        if (!this.validated) {
            throw new IllegalArgumentException("Must call buildClient before executing methods.");
        }
        
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String url = String.format("%s/topic/%s/bulk-produce",
                    this.buildEndpoint(),
                    this.topicId);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.writeApiKey);
            httpPost.addHeader("Content-type", this.format);
            
            String jsonString = MAPPER.writeValueAsString(events);
            HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
            httpPost.setEntity(entity);
            
            CloseableHttpResponse response;
            
            response = httpclient.execute(httpPost);
            TopicBulkResponse tbr = processBulkResponse(response);
            response.close();
            
            return tbr;
        }
    }
    
}