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

    public static String hashPass(String pass) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(pass.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (Exception e) {
            return null;
        }
    }
}
