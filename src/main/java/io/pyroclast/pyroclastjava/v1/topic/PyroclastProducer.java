package io.pyroclast.pyroclastjava.v1.topic;

import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncCancelledCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncFailCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncSuccessCallback;
import io.pyroclast.pyroclastjava.v1.topic.parsers.BulkProduceEventsParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.ProduceEventParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.ResponseParser;
import io.pyroclast.pyroclastjava.v1.topic.responses.ProducedEventResult;
import io.pyroclast.pyroclastjava.v1.topic.responses.ProducedEventsResult;
import java.io.IOException;
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

public class PyroclastProducer {

    private static final ObjectMapper MAPPER;

    private final String topicId;
    private final String writeApiKey;
    private final String format;
    private final String endpoint;

    static {
        MAPPER = new ObjectMapper();
    }

    public PyroclastProducer(String topicId, String writeApiKey, String format, String endpoint) {
        this.topicId = topicId;
        this.writeApiKey = writeApiKey;
        this.format = format;
        this.endpoint = endpoint;
    }

    public ProducedEventResult send(Map<Object, Object> event) throws IOException, PyroclastAPIException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/produce", this.endpoint, this.topicId);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.writeApiKey);
            httpPost.addHeader("Content-type", this.format);

            String jsonString = MAPPER.writeValueAsString(event);
            HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
            httpPost.setEntity(entity);

            CloseableHttpResponse response;

            response = httpClient.execute(httpPost);
            ResponseParser<ProducedEventResult> parser = new ProduceEventParser();
            ProducedEventResult result = parser.parseResponse(response, MAPPER);
            response.close();

            return result;
        }
    }

    public ProducedEventsResult send(List<Map<Object, Object>> events) throws IOException, PyroclastAPIException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/bulk-produce", endpoint, this.topicId);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.writeApiKey);
            httpPost.addHeader("Content-type", this.format);

            String jsonString = MAPPER.writeValueAsString(events);
            HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
            httpPost.setEntity(entity);

            CloseableHttpResponse response;

            response = httpClient.execute(httpPost);
            ResponseParser<ProducedEventsResult> parser = new BulkProduceEventsParser();
            ProducedEventsResult tbr = parser.parseResponse(response, MAPPER);
            response.close();

            return tbr;
        }
    }

    public void send(Map<Object, Object> event, AsyncSuccessCallback<ProducedEventResult> onSuccess, AsyncFailCallback onFail, AsyncCancelledCallback onCancel) throws IOException {
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();

        String url = String.format("%s/%s/produce", this.endpoint, this.topicId);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", this.writeApiKey);
        httpPost.addHeader("Content-type", this.format);

        String jsonString = MAPPER.writeValueAsString(event);
        HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
        httpPost.setEntity(entity);

        ResponseParser<ProducedEventResult> parser = new ProduceEventParser();
        AsyncCallback cb = new AsyncCallback(httpClient, parser, MAPPER, onSuccess, onFail, onCancel);
        httpClient.execute(httpPost, cb);
    }

    public void send(List<Map<Object, Object>> events, AsyncSuccessCallback<ProducedEventsResult> onSuccess, AsyncFailCallback onFail, AsyncCancelledCallback onCancel) throws IOException, InterruptedException {
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();

        String url = String.format("%s/%s/bulk-produce", this.endpoint, this.topicId);
        System.out.println(url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", this.writeApiKey);
        httpPost.addHeader("Content-type", this.format);

        String jsonString = MAPPER.writeValueAsString(events);
        HttpEntity entity = new ByteArrayEntity(jsonString.getBytes());
        httpPost.setEntity(entity);

        ResponseParser<ProducedEventsResult> parser = new BulkProduceEventsParser();
        AsyncCallback cb = new AsyncCallback(httpClient, parser, MAPPER, onSuccess, onFail, onCancel);
        httpClient.execute(httpPost, cb);
    }

}
