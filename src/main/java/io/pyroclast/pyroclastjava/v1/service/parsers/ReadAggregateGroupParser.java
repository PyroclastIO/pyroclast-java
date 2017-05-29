package io.pyroclast.pyroclastjava.v1.service.parsers;

import io.pyroclast.pyroclastjava.v1.ResponseParser;
import io.pyroclast.pyroclastjava.v1.exceptions.MalformedEventException;
import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnauthorizedAccessException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnknownAPIException;
import io.pyroclast.pyroclastjava.v1.service.responses.ReadAggregateGroupResult;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class ReadAggregateGroupParser implements ResponseParser<ReadAggregateGroupResult> {

    @Override
    public ReadAggregateGroupResult parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException, PyroclastAPIException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                HttpEntity entity = response.getEntity();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer, "UTF-8");
                String json = writer.toString();

                return mapper.readValue(json, ReadAggregateGroupResult.class);
            case 400:
                throw new MalformedEventException();

            case 401:
                throw new UnauthorizedAccessException();

            default:
                throw new UnknownAPIException(response.getStatusLine().toString());
        }
    }
}
