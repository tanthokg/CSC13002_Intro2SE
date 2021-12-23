package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Comment {
    private String authorName;
    private String content;
    private Timestamp postTime;

    public Comment() {}

    public Comment(String authorName, String content, Timestamp postTime) {
        this.authorName = authorName;
        this.content = content;
        this.postTime = postTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getPostTime() {
        return postTime;
    }
}
