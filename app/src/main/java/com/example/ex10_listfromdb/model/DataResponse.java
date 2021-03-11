package com.example.ex10_listfromdb.model;

public class DataResponse<T> {


    public enum RequestStatus {
        LOADING,
        ERROR,
        SUCCESS_LOCAL,
        SUCCESS_SERVER,
        NO_DATA
    }

    private T body;
    private RequestStatus requestStatus = RequestStatus.NO_DATA;

    public DataResponse(T body){
        this.body = body;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
