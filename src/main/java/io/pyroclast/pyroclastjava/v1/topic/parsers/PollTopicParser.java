package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.ResponseParser;
import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnauthorizedAccessException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnknownAPIException;
import io.pyroclast.pyroclastjava.v1.topic.responses.PollTopicResult;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class PollTopicParser implements ResponseParser<PollTopicResult> {

    @Override
    public PollTopicResult parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException, PyroclastAPIException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                return mapper.readValue(json, PollTopicResult.class);

            case 401:
                throw new UnauthorizedAccessException();

            default:
                throw new UnknownAPIException(response.getStatusLine().toString());
        }
    }

}
