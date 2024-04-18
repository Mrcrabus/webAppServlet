package org.example.webappservlet.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private long id;
    private String username;

    private List<Book> books;
    private Set<Library> libraries = new HashSet<>();
}
