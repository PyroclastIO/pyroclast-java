package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.topic.responses.PollTopicResponse;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class PollTopicParser implements ResponseParser<PollTopicResponse> {

    @Override
    public PollTopicResponse parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                return mapper.readValue(json, PollTopicResponse.class);

            case 401:
                return new PollTopicResponse(false, "API key unauthorized to perform this action.");

            default:
                return new PollTopicResponse(false, response.getStatusLine().toString());
        }
    }

}
