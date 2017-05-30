package io.pyroclast.pyroclastjava.v1.deployment;

import io.pyroclast.pyroclastjava.v1.deployment.parsers.ReadAggregatesParser;
import io.pyroclast.pyroclastjava.v1.ResponseParser;
import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.deployment.parsers.ReadAggregateGroupParser;
import io.pyroclast.pyroclastjava.v1.deployment.parsers.ReadAggregateParser;
import io.pyroclast.pyroclastjava.v1.deployment.responses.ReadAggregateGroupResult;
import io.pyroclast.pyroclastjava.v1.deployment.responses.ReadAggregateResult;
import io.pyroclast.pyroclastjava.v1.deployment.responses.ReadAggregatesResult;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

public class PyroclastDeploymentClient {

    private static final String VERSION;
    private static final String DEFAULT_REGION;
    private static final String FORMAT;
    private static final ObjectMapper MAPPER;

    private String deploymentId;
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

    public PyroclastDeploymentClient() {
        this.region = "us-east-1";
        this.validated = false;
    }

    public PyroclastDeploymentClient withDeploymentId(String topicId) {
        this.deploymentId = topicId;
        return this;
    }

    public PyroclastDeploymentClient withReadApiKey(String readApiKey) {
        this.readApiKey = readApiKey;
        return this;
    }

    public PyroclastDeploymentClient withRegion(String region) {
        this.region = region;
        return this;
    }

    public PyroclastDeploymentClient withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public PyroclastDeploymentClient buildClient() {
        if (this.deploymentId == null) {
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
            return String.format("%s/%s/deployments", this.endpoint, VERSION);
        } else if (this.region != null) {
            return String.format("https://api.%s.pyroclast.io/%s/deployments", this.region, VERSION);
        } else {
            return String.format("https://api.%s.pyroclast.io/%s/deployments", DEFAULT_REGION, VERSION);
        }
    }

    public ReadAggregatesResult readAggregates() throws IOException, PyroclastAPIException {
        ensureBaseAttributes();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/aggregates", this.buildEndpoint(), this.deploymentId);
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

    public ReadAggregateResult readAggregate(String aggregateName) throws IOException, PyroclastAPIException {
        ensureBaseAttributes();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/aggregates/%s",
                    this.buildEndpoint(), this.deploymentId, aggregateName);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization", this.readApiKey);
            httpGet.addHeader("Content-type", FORMAT);

            ReadAggregateResult result;
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                ResponseParser<ReadAggregateResult> parser = new ReadAggregateParser();
                result = parser.parseResponse(response, MAPPER);
            }

            return result;
        }
    }

    public ReadAggregateGroupResult readAggregateGroup(String aggregateName, String groupName) throws IOException, PyroclastAPIException {
        ensureBaseAttributes();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/aggregates/%s/group/%s",
                    this.buildEndpoint(), this.deploymentId, aggregateName, groupName);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization", this.readApiKey);
            httpGet.addHeader("Content-type", FORMAT);

            ReadAggregateGroupResult result;
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                ResponseParser<ReadAggregateGroupResult> parser = new ReadAggregateGroupParser();
                result = parser.parseResponse(response, MAPPER);
            }

            return result;
        }
    }

}
