package org.example.webappservlet.books.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webappservlet.books.services.BookService;
import org.example.webappservlet.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/edit-book", name = "EditBook")
public class EditBook extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            bookService = new BookService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book newBook = objectMapper.readValue(request.getReader(), Book.class);
        try {

            boolean success = bookService.updateBook(newBook);
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Ok");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Fail");
            }

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error getting book: " + e.getMessage());
        }
    }
}
