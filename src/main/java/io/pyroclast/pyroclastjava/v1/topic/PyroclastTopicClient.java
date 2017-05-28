package io.pyroclast.pyroclastjava.v1.topic;

import io.pyroclast.pyroclastjava.v1.topic.parsers.PollTopicParser;
import io.pyroclast.pyroclastjava.v1.topic.responses.BulkProduceEventResponse;
import io.pyroclast.pyroclastjava.v1.topic.responses.ProduceEventResponse;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncSuccessCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncFailCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncCancelledCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncCallback;
import io.pyroclast.pyroclastjava.v1.topic.parsers.BulkProduceEventsParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.ProduceEventParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.ResponseParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.SubscribeToTopicParser;
import io.pyroclast.pyroclastjava.v1.topic.responses.PollTopicResponse;
import io.pyroclast.pyroclastjava.v1.topic.responses.SubscribeToTopicResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
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

    public ProduceEventResponse produceEvent(Map<Object, Object> event) throws IOException {
        ensureBaseAttributes();
        ensureWriteApiKey();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/produce",
                    this.buildEndpoint(), this.topicId);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.writeApiKey);
            httpPost.addHeader("Content-type", this.format);

            String jsonString = MAPPER.writeValueAsString(event);
            HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
            httpPost.setEntity(entity);

            CloseableHttpResponse response;

            response = httpClient.execute(httpPost);
            ResponseParser<ProduceEventResponse> parser = new ProduceEventParser();
            ProduceEventResponse tr = parser.parseResponse(response, MAPPER);
            response.close();

            return tr;
        }
    }

    public BulkProduceEventResponse produceEvents(List<Map<Object, Object>> events) throws IOException {
        ensureBaseAttributes();
        ensureWriteApiKey();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/bulk-produce",
                    this.buildEndpoint(),
                    this.topicId);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.writeApiKey);
            httpPost.addHeader("Content-type", this.format);

            String jsonString = MAPPER.writeValueAsString(events);
            HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
            httpPost.setEntity(entity);

            CloseableHttpResponse response;

            response = httpClient.execute(httpPost);
            ResponseParser<BulkProduceEventResponse> parser = new BulkProduceEventsParser();
            BulkProduceEventResponse tbr = parser.parseResponse(response, MAPPER);
            response.close();

            return tbr;
        }
    }

    public void produceEventAsync(Map<Object, Object> event, AsyncSuccessCallback<ProduceEventResponse> onSuccess, AsyncFailCallback onFail, AsyncCancelledCallback onCancel) throws IOException {
        ensureBaseAttributes();
        ensureWriteApiKey();

        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();

        String url = String.format("%s/%s/produce",
                this.buildEndpoint(), this.topicId);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", this.writeApiKey);
        httpPost.addHeader("Content-type", this.format);

        String jsonString = MAPPER.writeValueAsString(event);
        HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
        httpPost.setEntity(entity);

        ResponseParser<ProduceEventResponse> parser = new ProduceEventParser();
        AsyncCallback cb = new AsyncCallback(httpClient, parser, MAPPER, onSuccess, onFail, onCancel);
        httpClient.execute(httpPost, cb);
    }

    public void produceEventsAsync(List<Map<Object, Object>> events, AsyncSuccessCallback<BulkProduceEventResponse> onSuccess, AsyncFailCallback onFail, AsyncCancelledCallback onCancel) throws IOException, InterruptedException {
        ensureBaseAttributes();
        ensureWriteApiKey();

        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();

        String url = String.format("%s/%s/bulk-produce",
                this.buildEndpoint(), this.topicId);
        System.out.println(url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", this.writeApiKey);
        httpPost.addHeader("Content-type", this.format);

        String jsonString = MAPPER.writeValueAsString(events);
        HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
        httpPost.setEntity(entity);

        ResponseParser<BulkProduceEventResponse> parser = new BulkProduceEventsParser();
        AsyncCallback cb = new AsyncCallback(httpClient, parser, MAPPER, onSuccess, onFail, onCancel);
        httpClient.execute(httpPost, cb);
    }

    public SubscribeToTopicResponse subscribeToTopic(String subscriptionName) throws IOException {
        ensureBaseAttributes();
        ensureReadApiKey();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/subscribe/%s",
                    this.buildEndpoint(), this.topicId, subscriptionName);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.readApiKey);
            httpPost.addHeader("Content-type", this.format);
            
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                ResponseParser<SubscribeToTopicResponse> parser = new SubscribeToTopicParser();
                SubscribeToTopicResponse str = parser.parseResponse(response, MAPPER);
                return str;
            }
        }
    }
    
    public PollTopicResponse pollTopic(String subscriptionName) throws IOException {
        ensureBaseAttributes();
        ensureReadApiKey();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/poll/%s",
                    this.buildEndpoint(), this.topicId, subscriptionName);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.readApiKey);
            httpPost.addHeader("Content-type", this.format);
            
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                ResponseParser<PollTopicResponse> parser = new PollTopicParser();
                PollTopicResponse ptr = parser.parseResponse(response, MAPPER);
                return ptr;
            }
        }
    }

}
