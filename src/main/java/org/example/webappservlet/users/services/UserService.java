package org.example.webappservlet.users.services;

import org.example.webappservlet.model.Book;
import org.example.webappservlet.model.User;
import org.example.webappservlet.users.dto.AddUserDTO;
import org.example.webappservlet.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final Connection connection;

    public UserService() throws SQLException {
        connection = DatabaseConnection.getConnection();
        createUsersTableIfNotExists();
    }

    public UserService(Connection connection) throws SQLException {
        this.connection = connection;
        createUsersTableIfNotExists();
    }



    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.id, u.username, b.id as book_id, b.title, b.author, b.year " +
                "FROM users u " +
                "LEFT JOIN books b ON u.id = b.user_id";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            User currentUser = null;
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("username");

                if (currentUser == null || currentUser.getId() != userId) {
                    currentUser = new User(userId, userName);
                    users.add(currentUser);
                }

                int bookId = resultSet.getInt("book_id");
                if (bookId > 0) {
                    String bookTitle = resultSet.getString("title");
                    String bookAuthor = resultSet.getString("author");
                    int bookYear = resultSet.getInt("year");
                    currentUser.getBooks().add(new Book(bookId, bookTitle, bookAuthor, bookYear, userId));
                }
            }
        }
        return users;
    }


    public boolean addUser(AddUserDTO user) throws SQLException {
        String query = "INSERT INTO users (username) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void createUsersTableIfNotExists() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY,"
                + "username VARCHAR(255) NOT NULL"
                + ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }
}
