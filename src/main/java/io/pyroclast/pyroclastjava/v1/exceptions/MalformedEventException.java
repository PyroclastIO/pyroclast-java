package io.pyroclast.pyroclastjava.v1.exceptions;

public class MalformedEventException extends PyroclastAPIException {

    public MalformedEventException() {
        super("Event data was malformed");
    }

    public MalformedEventException(String msg) {
        super(msg);
    }
}
