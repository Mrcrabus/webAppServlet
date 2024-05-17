package org.example.webappservlet.libraries.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webappservlet.libraries.dto.AddLibraryDTO;
import org.example.webappservlet.libraries.services.LibraryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/add-library", name = "AddLibrary")
public class AddLibrary extends HttpServlet {
    private LibraryService libraryService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            libraryService = new LibraryService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddLibraryDTO newLibraryDTO = objectMapper.readValue(request.getReader(), AddLibraryDTO.class);

        try {
            boolean success = libraryService.addLibrary(newLibraryDTO);
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("Library successfully added");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Failed to add library");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error adding library: " + e.getMessage());
        }
    }

}

