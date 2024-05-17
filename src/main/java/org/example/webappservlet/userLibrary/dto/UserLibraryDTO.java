package org.example.webappservlet.userLibrary.dto;

public class UserLibraryDTO {
    public Integer userId;
    public Integer libraryId;

    public UserLibraryDTO(Integer libraryId, Integer userId) {
        this.libraryId = libraryId;
        this.userId = userId;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public Integer getUserId() {
        return userId;
    }




}
