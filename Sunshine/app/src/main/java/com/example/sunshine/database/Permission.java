package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Permission extends PermissionId {

    private Timestamp permissionTime;

    public Permission() { }

    public Permission(Timestamp permissionTime)
    {
        this.permissionTime = permissionTime;
    }

    public Timestamp getPermissionTime() {
        return permissionTime;
    }

    public void setPermissionTime(Timestamp permissionTime)
    {
        this.permissionTime = permissionTime;
    }
}