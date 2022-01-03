package com.example.sunshine.database;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class PermissionId {
    @Exclude
    public String permissionId;

    public <T extends PermissionId> T withId(@NonNull final String id) {
        this.permissionId = id;
        return (T) this;
    }
}