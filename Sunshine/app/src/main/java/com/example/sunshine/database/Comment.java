package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Comment {
    private String username;
    private String content;
    private Timestamp postTime;

    public Comment() {}

    public Comment(String username, String content, Timestamp postTime) {
        this.username = username;
        this.content = content;
        this.postTime = postTime;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getPostTime() {
        return postTime;
    }
}
