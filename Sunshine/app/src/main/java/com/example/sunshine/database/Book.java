package com.example.sunshine.database;

import java.util.List;

public class Book extends BookId{
    private String name;
    private String author;
    private String imageUri;
    private List<String> categories;
    private int publishedYear;

    public Book() {}

    public Book(String name, String author, String imageUri, List<String> categories, int publishedYear) {
        this.name = name;
        this.author = author;
        this.imageUri = imageUri;
        this.categories = categories;
        this.publishedYear = publishedYear;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUri() {
        return imageUri;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
}
