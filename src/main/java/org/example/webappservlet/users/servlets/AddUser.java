package org.example.webappservlet.users.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.webappservlet.users.dto.AddUserDTO;
import org.example.webappservlet.users.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/user")
public class AddUser extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userService = new UserService();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddUserDTO newUser = objectMapper.readValue(request.getReader(), AddUserDTO.class);

        try {
            boolean success = userService.addUser(newUser);
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("User successfully added");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Failed to add user");
            }
        } catch (SQLException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error adding user: " + e.getMessage());
        }
    }


}
