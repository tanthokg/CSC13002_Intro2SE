package com.example.sunshine.database;

import java.util.List;

public class Book {
    private int id;
    private String author;
    private List<String> categories;
    private int publishedYear;
    private List<Integer> reviewPosts;

    public Book() {}

    public Book(int id, String author, List<String> categories, int publishedYear, List<Integer> reviewPosts) {
        this.id = id;
        this.author = author;
        this.categories = categories;
        this.publishedYear = publishedYear;
        this.reviewPosts = reviewPosts;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public List<Integer> getReviewPosts() {
        return reviewPosts;
    }
}
