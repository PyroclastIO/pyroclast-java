package io.pyroclast.pyroclastjava.v1.topic;

import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.topic.parsers.PollTopicParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.ReadCommitParser;
import io.pyroclast.pyroclastjava.v1.topic.parsers.ResponseParser;
import io.pyroclast.pyroclastjava.v1.topic.responses.PollTopicResult;
import io.pyroclast.pyroclastjava.v1.topic.responses.ReadCommitResult;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

public class PyroclastConsumer {

    private static final ObjectMapper MAPPER;

    private final String topicId;
    private final String readApiKey;
    private final String format;
    private final String endpoint;
    private final String subscriptionName;

    static {
        MAPPER = new ObjectMapper();
    }

    public PyroclastConsumer(String topicId, String readApiKey, String format, String endpoint, String subscriptionName) {
        this.topicId = topicId;
        this.readApiKey = readApiKey;
        this.format = format;
        this.endpoint = endpoint;
        this.subscriptionName = subscriptionName;
    }

    public PollTopicResult pollTopic() throws IOException, PyroclastAPIException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/poll/%s",
                    this.endpoint, this.topicId, subscriptionName);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.readApiKey);
            httpPost.addHeader("Content-type", this.format);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                ResponseParser<PollTopicResult> parser = new PollTopicParser();
                PollTopicResult ptr = parser.parseResponse(response, MAPPER);
                return ptr;
            }
        }
    }

    public ReadCommitResult commit() throws IOException, PyroclastAPIException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format("%s/%s/poll/%s/commit",
                    this.endpoint, this.topicId, subscriptionName);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", this.readApiKey);
            httpPost.addHeader("Content-type", this.format);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                ResponseParser<ReadCommitResult> parser = new ReadCommitParser();
                ReadCommitResult ptr = parser.parseResponse(response, MAPPER);
                return ptr;
            }
        }
    }

}
