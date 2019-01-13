package com.example.mfekr.popularmoviesmaster.Model;

public class Review {

    private String author;
    private String content;

    /**
     * No args constructor for use in serialization
     *
     */
    public Review() {
    }

    /**
     *
     * @param content
     * @param author
     */
    public Review(String author, String content) {
        super();
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}