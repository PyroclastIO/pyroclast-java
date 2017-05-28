package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.topic.responses.ProduceEventResponse;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class ProduceEventParser implements ResponseParser<ProduceEventResponse> {

    @Override
    public ProduceEventResponse parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                return mapper.readValue(json, ProduceEventResponse.class);
            case 400:
                return new ProduceEventResponse(false, "Event data was malformed.");

            case 401:
                return new ProduceEventResponse(false, "API key unauthorized to perform this action.");

            default:
                return new ProduceEventResponse(false, response.getStatusLine().toString());
        }
    }

}
