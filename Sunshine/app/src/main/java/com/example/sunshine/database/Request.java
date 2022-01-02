package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Request extends RequestId {

    private Timestamp requestTime;

    public Request() {
    }

    public Request(Timestamp requestTime) {
        this.requestTime = requestTime; }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

}