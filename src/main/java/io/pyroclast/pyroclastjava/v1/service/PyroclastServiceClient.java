package io.pyroclast.pyroclastjava.v1.service;

import io.pyroclast.pyroclastjava.v1.service.parsers.ReadAggregatesParser;
import io.pyroclast.pyroclastjava.v1.ResponseParser;
import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.service.responses.ReadAggregatesResult;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

public class PyroclastServiceClient {

    private static final String VERSION;
    private static final String DEFAULT_REGION;
    private static final String FORMAT;
    private static final ObjectMapper MAPPER;

    private String serviceId;
    private String readApiKey;
    private String endpoint;
    private String region;
    private boolean validated;

    static {
        VERSION = "v1";
        DEFAULT_REGION = "us-east-1";
        FORMAT = "application/json";
        MAPPER = new ObjectMapper();
    }

    public PyroclastServiceClient() {
        this.region = "us-east-1";
        this.validated = false;
    }

    public PyroclastServiceClient withServiceId(String topicId) {
        this.serviceId = topicId;
        return this;
    }

    public PyroclastServiceClient withReadApiKey(String readApiKey) {
        this.readApiKey = readApiKey;
        return this;
    }

    public PyroclastServiceClient withRegion(String region) {
        this.region = region;
        return this;
    }

    public PyroclastServiceClient withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public PyroclastServiceClient buildClient() {
        if (this.serviceId == null) {
            throw new IllegalArgumentException("Topic ID must be configured.");
        }

        if (this.readApiKey == null) {
            throw new IllegalArgumentException("Read API Key must be configured.");
        }

        this.validated = true;
        return this;
    }

    private void ensureBaseAttributes() {
        if (!this.validated) {
            throw new IllegalArgumentException("Must call buildClient before executing API methods on this object.");
        }
    }
        
    private String buildEndpoint() {
        if (this.endpoint != null) {
            return String.format("%s/%s/services", this.endpoint, VERSION);
        } else if (this.region != null) {
            return String.format("https://api.%s.pyroclast.io/%s/services", this.region, VERSION);
        } else {
            return String.format("https://api.%s.pyroclast.io/%s/services", DEFAULT_REGION, VERSION);
        }
    }

    public ReadAggregatesResult readAggregates() throws IOException, PyroclastAPIException {
        ensureBaseAttributes();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s", this.buildEndpoint(), this.serviceId);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization", this.readApiKey);
            httpGet.addHeader("Content-type", FORMAT);

            ReadAggregatesResult result;
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                ResponseParser<ReadAggregatesResult> parser = new ReadAggregatesParser();
                result = parser.parseResponse(response, MAPPER);
            }
            
            return result;
        }
    }

    public void readAggregate(String aggregateName) {
        throw new UnsupportedOperationException();

    }

    public void readAggregateGroup(String aggregateGroup) {
        throw new UnsupportedOperationException();
    }

}
