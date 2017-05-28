package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.topic.responses.ProduceEventResponse;
import io.pyroclast.pyroclastjava.v1.topic.responses.SubscribeToTopicResponse;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class SubscribeToTopicParser implements ResponseParser<SubscribeToTopicResponse> {

    @Override
    public SubscribeToTopicResponse parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                return mapper.readValue(json, SubscribeToTopicResponse.class);

            case 401:
                return new SubscribeToTopicResponse(false, "API key unauthorized to perform this action.");

            default:
                return new SubscribeToTopicResponse(false, response.getStatusLine().toString());
        }
    }
}
