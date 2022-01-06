package com.example.sunshine.database;

import com.example.sunshine.R;

public class Report {
    private int type;
    private int resourceID;
    private String userName;
    private String postName;
    private String time;

    public Report(int type, String userName, String postName, String time) {
        this.type = type;
        this.userName = userName;
        this.postName = postName;
        this.time = time;

        if (type == 1 || type == 0)
            resourceID = R.drawable.post;
        else if (type == 2)
            resourceID = R.drawable.comment;
        else if (type == 3)
            resourceID = R.drawable.notification_promoted;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String getReport()
    {
        String reported = "has been reported.";
        String review = "The <strong>review</strong> of ";
        String comment = "The comment of ";

        if (type == 1)
            return review;
        else if (type == 2)
            return comment;
        return reported;
    }

    public String toString()
    {
        String reported = "has been reported.";
        StringBuilder result = new StringBuilder();

        StringBuilder stylePostName = new StringBuilder();
        stylePostName.append(" <strong>").append(postName).append("</strong> ");

        StringBuilder styleUserName = new StringBuilder();
        styleUserName.append(" <strong>").append(userName).append("</strong> ");

        if(type == 1)
            result.append(getReport()).append(stylePostName).append("of" + styleUserName).append(reported);
        else if (type == 2)
            result.append(getReport()).append(styleUserName + "on the <strong>review</strong> of").append(stylePostName).append(reported);
        else if (type == 3)
            result.append(styleUserName).append(getReport());

        return result.toString();
    }
}
