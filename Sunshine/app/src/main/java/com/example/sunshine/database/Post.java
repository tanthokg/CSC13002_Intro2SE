package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Post extends PostId {
    private String author;
    private String bookName;
    private String content;
    private String status;
    private Timestamp postTime;
    private int upvote;
    private int downvote;
    private int commentCount;

    public Post() {}

    public Post(String author, String bookName, String content, Timestamp postTime, int upvote, int downvote, String status, int commentCount) {
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

    public String getStatus() {
        return status;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
