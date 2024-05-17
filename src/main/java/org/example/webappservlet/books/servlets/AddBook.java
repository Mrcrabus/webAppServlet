package org.example.webappservlet.books.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webappservlet.books.services.BookService;
import org.example.webappservlet.books.dto.AddBookDTO;
import org.example.webappservlet.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/add-book", name = "BookServlet", loadOnStartup = 0)
public class AddBook extends HttpServlet {
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
        AddBookDTO newBookDTO = objectMapper.readValue(request.getReader(), AddBookDTO.class);

        Book newBook = convertToBook(newBookDTO);

        try {
            boolean success = bookService.addBook(newBook);
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("Ok");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Fail");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error adding book to database: " + e.getMessage());
        }
    }

    private Book convertToBook(AddBookDTO bookDTO) {
        return new Book(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getYear());
    }

}
