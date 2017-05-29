package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.ResponseParser;
import io.pyroclast.pyroclastjava.v1.exceptions.MalformedEventException;
import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnauthorizedAccessException;
import io.pyroclast.pyroclastjava.v1.exceptions.UnknownAPIException;
import io.pyroclast.pyroclastjava.v1.topic.responses.ReadCommitResult;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public class ReadCommitParser implements ResponseParser<ReadCommitResult> {

    @Override
    public ReadCommitResult parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException, PyroclastAPIException {
        int status = response.getStatusLine().getStatusCode();

        switch (status) {
            case 200:
                return new ReadCommitResult(true);
            case 400:
                throw new MalformedEventException();

            case 401:
                throw new UnauthorizedAccessException();

            default:
                throw new UnknownAPIException(response.getStatusLine().toString());
        }
    }

}
