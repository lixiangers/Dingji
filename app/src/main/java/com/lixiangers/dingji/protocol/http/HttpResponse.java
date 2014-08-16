package com.lixiangers.dingji.protocol.http;

public class HttpResponse<T> {
    private String jsonrpc;
    private ErrorMessage error;
    private String id;
    private T result;

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }

    public T getResponseParams() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean noErrorMessage() {
        return getError() == null;
    }
}