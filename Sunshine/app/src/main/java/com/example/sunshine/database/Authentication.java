package com.example.sunshine.database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class Authentication {
    private String username;
    private String password;
    private List<String> questions;
    private List<String> answers;

    public Authentication() {}

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Authentication(String username, String password, List<String> questions, List<String> answers) {
        this.username = username;
        this.password = password;
        this.questions = questions;
        this.answers = answers;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public void setAnswers(List<String> answers) {
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

    //TODO: hash passs
}
