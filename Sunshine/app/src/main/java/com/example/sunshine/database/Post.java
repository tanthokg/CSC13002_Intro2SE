package com.example.sunshine.database;

public class Post {
    private int id;
    private String author;
    private String content;
    private String postTime;
    private int upvote;
    private int downvote;
    private int status;
    private int commentCount;

    public Post() {}

    public Post(int id, String author, String content, String postTime, int upvote, int downvote, int status, int commentCount) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.postTime = postTime;
        this.upvote = upvote;
        this.downvote = downvote;
        this.status = status;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getPostTime() {
        return postTime;
    }

    public int getUpvote() {
        return upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public int getStatus() {
        return status;
    }

    public int getCommentCount() {
        return commentCount;
    }
}
