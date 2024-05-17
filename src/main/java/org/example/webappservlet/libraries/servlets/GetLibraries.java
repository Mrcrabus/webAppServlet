package org.example.webappservlet.libraries.servlets;

import com.google.gson.Gson;
import org.example.webappservlet.libraries.services.LibraryService;
import org.example.webappservlet.model.Library;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/get-libraries", name = "GetLibraries")
public class GetLibraries extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Library> libraries = libraryService.getAllLibraries();
            String jsonLibraries = new Gson().toJson(libraries);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonLibraries);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error getting libraries: " + e.getMessage());
        }
    }
}
