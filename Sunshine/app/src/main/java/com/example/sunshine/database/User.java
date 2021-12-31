package com.example.sunshine.database;

import java.util.List;

public class User {
    private String username;
    private String fullname;
    private boolean type;
    private String birthday;
    private boolean gender;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
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
}
