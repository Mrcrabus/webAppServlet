package org.example.webappservlet.books.servlets;

import org.example.webappservlet.books.services.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/remove-user-from-book")
public class RemoveUserFromBook  extends HttpServlet {
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookIdParam = request.getParameter("id");
        try {
            int bookId = Integer.parseInt(bookIdParam);
            boolean success = bookService.removeUserFromBook(bookId);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Ok");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Fail");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid book ID: " + bookIdParam);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error deleting user from book: " + e.getMessage());
        }
    }

}
