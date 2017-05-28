package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnauthorizedAccessException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnknownAPIException;
import io.pyroclast.pyroclastjava.v1.topic.PyroclastConsumer;
import io.pyroclast.pyroclastjava.v1.topic.responses.ProducedEventResult;
import io.pyroclast.pyroclastjava.v1.topic.responses.SubscribeToTopicResult;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class SubscribeToTopicParser implements ResponseParser<PyroclastConsumer> {

    private final String readApiKey;
    private final String topicId;
    private final String format;
    private final String endpoint;
    private final String subscriptionName;

    public SubscribeToTopicParser(String topicId, String readApiKey, String format, String endpoint, String subscriptionName) {
        this.topicId = topicId;
        this.readApiKey = readApiKey;
        this.format = format;
        this.endpoint = endpoint;
        this.subscriptionName = subscriptionName;
    }

    @Override
    public PyroclastConsumer parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException, PyroclastAPIException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                SubscribeToTopicResult result = mapper.readValue(json, SubscribeToTopicResult.class);

                if (result.getSuccess()) {
                    return new PyroclastConsumer(topicId, readApiKey, format, endpoint, subscriptionName);
                } else {
                    throw new UnknownAPIException(result.getFailureReason());
                }

            case 401:
                throw new UnauthorizedAccessException();

            default:
                throw new UnknownAPIException(response.getStatusLine().toString());
        }
    }
}
