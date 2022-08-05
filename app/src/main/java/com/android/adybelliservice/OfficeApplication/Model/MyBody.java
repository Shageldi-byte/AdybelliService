package com.android.adybelliservice.OfficeApplication.Model;

public class MyBody<T> {
    private Boolean error;
    private Message message;
    private T body;

    public MyBody(Boolean error, Message message, T body) {
        this.error = error;
        this.message = message;
        this.body = body;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
