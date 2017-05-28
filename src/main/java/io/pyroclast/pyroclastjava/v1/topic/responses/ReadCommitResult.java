package io.pyroclast.pyroclastjava.v1.topic.responses;

import io.pyroclast.pyroclastjava.v1.topic.deserializers.ReadCommitResponseDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ReadCommitResponseDeserializer.class)
public class ReadCommitResult {

    private final boolean success;
    private final String reason;

    public ReadCommitResult(boolean success) {
        this.success = success;
        this.reason = null;
    }

    public ReadCommitResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getFailureReason() {
        return this.reason;
    }

}
