package io.pyroclast.pyroclastjava.v1.topic;

import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.ResponseParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.SubscribeToTopicParser;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

public class PyroclastTopicClient {

    private static final String VERSION;
    private static final String DEFAULT_REGION;
    private static final ObjectMapper MAPPER;

    private String topicId;
    private String readApiKey;
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

    public PyroclastTopicClient withReadApiKey(String readApiKey) {
        this.readApiKey = readApiKey;
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

        if ((this.readApiKey == null) && (this.writeApiKey == null)) {
            throw new IllegalArgumentException("Read or Write API Key must be configured.");
        }

        this.validated = true;
        return this;
    }

    private void ensureBaseAttributes() {
        if (!this.validated) {
            throw new IllegalArgumentException("Must call buildClient before executing API methods on this object.");
        }
    }

    public void ensureReadApiKey() {
        if (this.readApiKey == null) {
            throw new IllegalArgumentException("Must configure client with a Read API Key to use this method.");
        }
    }

    public void ensureWriteApiKey() {
        if (this.writeApiKey == null) {
            throw new IllegalArgumentException("Must configure client with a Write API Key to use this method.");
        }
    }

    private String buildEndpoint() {
        if (this.endpoint != null) {
            return String.format("%s/%s/topics", this.endpoint, VERSION);
        } else if (this.region != null) {
            return String.format("https://api.%s.pyroclast.io/%s/topics", this.region, VERSION);
        } else {
            return String.format("https://api.%s.pyroclast.io/%s/topics", DEFAULT_REGION, VERSION);
        }
    }

    public PyroclastProducer createProducer() {
        ensureBaseAttributes();
        ensureWriteApiKey();

        return new PyroclastProducer(topicId, writeApiKey, format, buildEndpoint());
    }

    public PyroclastConsumer createConsumer(String subscriptionName) throws IOException, PyroclastAPIException {
        ensureBaseAttributes();
        ensureReadApiKey();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/subscribe/%s",
                    this.buildEndpoint(), this.topicId, subscriptionName);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.readApiKey);
            httpPost.addHeader("Content-type", this.format);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                ResponseParser<PyroclastConsumer> parser = new SubscribeToTopicParser(this.topicId, this.readApiKey, this.format, buildEndpoint(), subscriptionName);
                return parser.parseResponse(response, MAPPER);
            }
        }
    }

}
