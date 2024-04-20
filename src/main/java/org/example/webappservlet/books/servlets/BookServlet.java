package org.example.webappservlet.books.servlets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webappservlet.model.Book;
import org.example.webappservlet.books.services.BookService;


@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private BookService bookService;
    private ObjectMapper objectMapper;


    @Override
    public void init() throws ServletException {
        super.init();
        try {
            bookService = new BookService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Book> books = bookService.getAllBooks();
            String jsonBooks = objectMapper.writeValueAsString(books);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonBooks);
        } catch (SQLException e) {
            throw new ServletException("Error getting books", e);
        }
    }

}
