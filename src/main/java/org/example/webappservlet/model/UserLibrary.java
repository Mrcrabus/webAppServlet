package org.example.webappservlet.model;

public class UserLibrary {
    public String userId;
    public String libraryId;

    public UserLibrary(String libraryId, String userId) {
        this.libraryId = libraryId;
        this.userId = userId;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public String getUserId() {
        return userId;
    }

}
