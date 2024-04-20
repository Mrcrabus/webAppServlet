package org.example.webappservlet.model;


public class Book {
    private long id;
    private String title;
    private String author;
    private int year;
    private User user;

    public Book(long id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }
}