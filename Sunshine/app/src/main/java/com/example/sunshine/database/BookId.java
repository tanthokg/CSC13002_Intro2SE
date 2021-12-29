package com.example.sunshine.database;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class BookId {
    @Exclude
    public String bookId;

    public <T extends BookId> T withId(@NonNull final String bookId)
    {
        this.bookId = bookId;
        return (T) this;
    }
}
