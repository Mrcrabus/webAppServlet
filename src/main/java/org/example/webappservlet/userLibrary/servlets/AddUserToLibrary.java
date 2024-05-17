package org.example.webappservlet.userLibrary.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webappservlet.libraries.services.LibraryService;
import org.example.webappservlet.userLibrary.dto.AddUserToLibraryDTO;
import org.example.webappservlet.userLibrary.services.UserLibraryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/add-user-library")
public class AddUserToLibrary extends HttpServlet {

    private UserLibraryService userLibraryService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userLibraryService = new UserLibraryService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddUserToLibraryDTO newPair = objectMapper.readValue(request.getReader(), AddUserToLibraryDTO.class);

        try {
            boolean success  = userLibraryService.addUserToLibrary(newPair);
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("User successfully added to library");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Failed to add library");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
