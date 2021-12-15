package com.example.sunshine.database;

import java.util.List;

public class Authentication {
    private String username;
    private String password;
    private List<String> questions;
    private List<String> answers;

    public Authentication() {}

    public Authentication(String username, String password, List<String> questions, List<String> answers) {
        this.username = username;
        this.password = password;
        this.questions = questions;
        this.answers = answers;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
