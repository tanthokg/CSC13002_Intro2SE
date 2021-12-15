package com.example.sunshine.database;

public class Comment {
    private int authorID;
    private String content;
    private String postTime;

    public Comment() {}

    public Comment(int authorID, String content, String postTime) {
        this.authorID = authorID;
        this.content = content;
        this.postTime = postTime;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getContent() {
        return content;
    }

    public String getPostTime() {
        return postTime;
    }
}
