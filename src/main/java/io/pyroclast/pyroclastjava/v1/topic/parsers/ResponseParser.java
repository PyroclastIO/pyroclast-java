package io.pyroclast.pyroclastjava.v1.topic.parsers;

import io.pyroclast.pyroclastjava.v1.exceptions.PyroclastAPIException;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

public interface ResponseParser<T> {
    
    public T parseResponse(HttpResponse response, ObjectMapper mapper) throws IOException, PyroclastAPIException;
    
}
