package org.example.webappservlet.userLibrary.services;

import org.example.webappservlet.model.UserLibrary;
import org.example.webappservlet.userLibrary.dto.AddUserToLibraryDTO;
import org.example.webappservlet.utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class UserLibraryService {
    private final Connection connection;

    public UserLibraryService() throws SQLException {

        connection = DatabaseConnection.getConnection();

    }

    public UserLibraryService(Connection connection) {
        this.connection = connection;
    }

    public List<UserLibrary> getAllUserLibrary() {
        List<UserLibrary> userLibraries = new ArrayList<>();
        String sql = "SELECT * FROM user_library";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String libraryId = resultSet.getString("library_id");
                userLibraries.add(new UserLibrary(userId, libraryId));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userLibraries;
    }

    public boolean addUserToLibrary(AddUserToLibraryDTO addUserToLibraryDTO) throws SQLException {
        String sql = "INSERT INTO user_library (user_id, library_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, addUserToLibraryDTO.getUserId());
            statement.setInt(2, addUserToLibraryDTO.getLibraryId());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
