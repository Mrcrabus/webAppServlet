package org.example.webappservlet.model;

import java.util.HashSet;
import java.util.Set;

public class Library {
    private long id;
    private String name;
    private Set<User> users = new HashSet<>();

}