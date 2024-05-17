package org.example.webappservlet.books.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webappservlet.books.services.BookService;
import org.example.webappservlet.books.dto.AddBookKeeperDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/add-book-keeper")
public class AddUserBook extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            bookService = new BookService();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddBookKeeperDTO newBookDTO = objectMapper.readValue(request.getReader(), AddBookKeeperDTO.class);

        try {
            boolean success = bookService.addBookToUser(newBookDTO);
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("Book successfully added to user");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Failed to add book to user");
            }
        } catch (SQLException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error adding book to user: " + e.getMessage());
        }
    }


}
