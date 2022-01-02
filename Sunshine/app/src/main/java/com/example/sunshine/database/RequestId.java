package com.example.sunshine.database;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class RequestId {
    @Exclude
    public String requestId;

    public <T extends RequestId> T withId(@NonNull final String id) {
        this.requestId = id;
        return (T) this;
    }
}