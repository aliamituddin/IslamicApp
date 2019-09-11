package com.example.islamicapp;

public class InstantMessage {
    private String message;
    private String author;

    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }
//requirement of FireBase to add this empty constructor
    public InstantMessage() {

    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
