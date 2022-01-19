package com.example.sunshine.user;

public class Category {

    private int resourceID;

    private String bookName;
    private String bookContent;

    private int upvoteCount;
    private int downvoteCount;
    //private int viewCount;

    public Category(int resourceID, String bookName,
                    String bookContent, int upvoteCount, int downvoteCount) {
        this.resourceID = resourceID;
        this.bookName = bookName;
        this.bookContent = bookContent;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;
        //this.viewCount = viewCount;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }

    public int getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public int getDownvoteCount() {
        return downvoteCount;
    }

    public void setDownvoteCount(int downvoteCount) {
        this.downvoteCount = downvoteCount;
    }

    /*public int getViewCount() {
        return viewCount;
    }*/

    /*public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }*/
}
