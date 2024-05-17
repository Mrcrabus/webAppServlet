package org.example.webappservlet.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;

    private List<Book> books = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(int id, String name) {
        this.id = id;
        this.username = name;
    }
}
