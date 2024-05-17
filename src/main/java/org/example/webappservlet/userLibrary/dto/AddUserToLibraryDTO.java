package org.example.webappservlet.userLibrary.dto;

public class AddUserToLibraryDTO {
    public Integer userId;
    public Integer libraryId;

    public Integer getUserId() {
        return userId != null ? userId : 0;
    }

    public Integer getLibraryId() {
        return libraryId != null ? libraryId : 0;
    }
}
