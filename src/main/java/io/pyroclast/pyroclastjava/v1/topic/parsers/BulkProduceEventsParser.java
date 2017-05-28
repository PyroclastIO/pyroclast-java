package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.topic.responses.BulkProduceEventResponse;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class BulkProduceEventsParser implements ResponseParser<BulkProduceEventResponse> {

    @Override
    public BulkProduceEventResponse parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                return mapper.readValue(json, BulkProduceEventResponse.class);
            case 400:
                return new BulkProduceEventResponse("Event data was malformed.");

            case 401:
                return new BulkProduceEventResponse("API key unauthorized to perform this action.");

            default:
                return new BulkProduceEventResponse(response.getStatusLine().toString());
        }
    }

}
