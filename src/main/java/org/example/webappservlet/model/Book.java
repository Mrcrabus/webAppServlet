package org.example.webappservlet.model;


public class Book {
    private long id;
    private String title;
    private String author;
    private int year;
    private Integer userId;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(long id, String title, String author, int year, Integer user) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.userId = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

}