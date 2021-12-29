package com.example.sunshine.database;

import com.google.firebase.Timestamp;

public class Post extends PostId {
    private String author;
    private String bookName;
    private String content;
    private String status;
    private Timestamp postTime;

    public Post() {}

    public Post(String author, String bookName, String content, Timestamp postTime, String status) {
        this.author = author;
        this.bookName = bookName;
        this.content = content;
        this.postTime = postTime;
        this.status = status;
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

    public String getStatus() {
        return status;
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
}
