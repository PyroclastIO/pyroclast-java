package io.pyroclast.pyroclastjava.v1.async;

import io.pyroclast.pyroclastjava.v1.topic.PyroclastTopicClient;
import io.pyroclast.pyroclastjava.v1.ResponseParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.codehaus.jackson.map.ObjectMapper;

public class AsyncCallback implements FutureCallback<HttpResponse> {

    private final CloseableHttpAsyncClient client;
    private final ResponseParser responseParser;
    private final ObjectMapper mapper;
    private final AsyncSuccessCallback onSuccess;
    private final AsyncFailCallback onFail;
    private final AsyncCancelledCallback onCancel;

    public AsyncCallback(CloseableHttpAsyncClient client, ResponseParser rp, ObjectMapper mapper, AsyncSuccessCallback onSuccess, AsyncFailCallback onFail, AsyncCancelledCallback onCancel) {
        this.client = client;
        this.responseParser = rp;
        this.mapper = mapper;
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.onCancel = onCancel;
    }

    @Override
    public void completed(HttpResponse t) {
        try {
            onSuccess.invoke(responseParser.parseResponse(t, mapper));
        } catch (Throwable ex) {
            Logger.getLogger(PyroclastTopicClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(AsyncCallback.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void failed(Exception excptn) {
        try {
            onFail.invoke(excptn);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(AsyncCallback.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void cancelled() {
        try {
            onCancel.invoke(-1);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(AsyncCallback.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
