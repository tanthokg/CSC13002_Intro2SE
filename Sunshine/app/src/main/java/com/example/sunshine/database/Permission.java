package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Permission extends PermissionId {

    private Timestamp permissionTime;
   // private String requestBy;

    public Permission() { }

    public Permission(Timestamp permissionTime)
    {
        this.permissionTime = permissionTime;
      //  this.requestBy=requestBy;
    }

    public Timestamp getPermissionTime() {
        return permissionTime;
    }
//    public String getRequestBy() {
//        return requestBy;
//    }

    public void setPermissionTime(Timestamp permissionTime)
    {
        this.permissionTime = permissionTime;
    }
//    public void setRequestBy(String postBy) {
//        this.postBy = postBy;
//    }
}