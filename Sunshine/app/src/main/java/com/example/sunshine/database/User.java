package com.example.sunshine.database;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String fullname;
    private boolean type;
    private String birthday;
    private boolean gender;
    private List<Integer> readLater;

    public User() {}

    public User(int id, String username, String fullname, boolean type, String birthday, boolean gender, List<Integer> readLater) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.type = type;
        this.birthday = birthday;
        this.gender = gender;
        this.readLater = readLater;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public boolean isType() {
        return type;
    }

    public String getBirthday() {
        return birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public List<Integer> getReadLater() {
        return readLater;
    }
}
