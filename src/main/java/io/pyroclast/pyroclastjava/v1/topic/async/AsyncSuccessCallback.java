package io.pyroclast.pyroclastjava.v1.topic.async;

public interface AsyncSuccessCallback<T> {
    public void invoke(T tr);
}
