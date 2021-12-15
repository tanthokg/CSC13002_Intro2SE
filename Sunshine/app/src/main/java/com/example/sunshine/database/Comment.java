package com.example.sunshine.database;

public class Comment {
    private String authorName;
    private String content;
    private String postTime;

    public Comment() {}

    public Comment(String authorName, String content, String postTime) {
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

    public String getPostTime() {
        return postTime;
    }
}
