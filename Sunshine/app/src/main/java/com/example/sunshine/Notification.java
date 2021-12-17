package com.example.sunshine;

import java.util.List;
import android.text.Html;

public class Notification {

    // type = 0 or 1: notification for upvote-downvote
    // type = 2: notification for comment
    // type = 3: notification for being promoted
    private int type;
    private int resourceID;
    private List<String> userName;
    private String postName;
    private int interactionCount;
    private String time;

    public Notification(int type, List<String> userName, String postName, int interactionCount, String time) {
        this.type = type;
        this.userName = userName;
        this.postName = postName;
        this.interactionCount = interactionCount;
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

    public List<String> getUserName() {
        return userName;
    }

    public void setUserName(List<String> userName) {
        this.userName = userName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(int interactionCount) {
        this.interactionCount = interactionCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String getNoti()
    {
        String upvoteNoti = "has upvoted your <strong>review</strong> of ";
        String downvoteNoti = "has downvoted your <strong>review</strong> of ";
        String commentNoti = " has commented on your <strong>review</strong> of ";
        String promoted = "You have been promoted to <strong>reviewer</strong> and is now " +
                        "able to create your own review post";

        if (type == 0)
            return downvoteNoti;
        else if (type == 1)
            return upvoteNoti;
        else if (type == 2)
            return commentNoti;
        else if (type == 3)
            return promoted;
        else
            return "";
    }

    public String toString()
    {
        if (type == 3)
            return getNoti();
        else
        {
            StringBuilder stylePostName = new StringBuilder();
            stylePostName.append("<strong>").append(postName).append("</strong>");

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < userName.size(); i++)
            {
                result.append(userName.get(i));
                result.append(", ");
            }


            result.append("and ").append(interactionCount).append(" more ");
            result.append(getNoti()).append(stylePostName).append(".");

            return result.toString();
        }
    }
}
