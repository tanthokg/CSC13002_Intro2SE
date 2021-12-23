package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Post {
    private String author;
    private String bookName;
    private String content;
    private Timestamp postTime;
    private int upvote;
    private int downvote;
    private int status;
    private int commentCount;

    public Post() {}

    public Post(String author, String bookName, String content, Timestamp postTime, int upvote, int downvote, int status, int commentCount) {
        this.author = author;
        this.bookName = bookName;
        this.content = content;
        this.postTime = postTime;
        this.upvote = upvote;
        this.downvote = downvote;
        this.status = status;
        this.commentCount = commentCount;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getPostTime() {
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
