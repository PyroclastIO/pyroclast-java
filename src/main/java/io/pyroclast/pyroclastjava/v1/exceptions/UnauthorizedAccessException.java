package io.pyroclast.pyroclastjava.v1.exceptions;

public class UnauthorizedAccessException extends PyroclastAPIException {

    public UnauthorizedAccessException() {
        super("API key unauthorized to perform this action.");
    }

    public UnauthorizedAccessException(String msg) {
        super(msg);
    }
}
