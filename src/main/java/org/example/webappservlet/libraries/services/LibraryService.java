package org.example.webappservlet.libraries.services;

import org.example.webappservlet.libraries.dto.AddLibraryDTO;
import org.example.webappservlet.libraries.dto.UpdateLibraryDTO;
import org.example.webappservlet.model.Library;
import org.example.webappservlet.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private final Connection connection;

    public LibraryService() throws SQLException {
        connection = DatabaseConnection.getConnection();
        createLibrariesTableIfNotExists();
    }

    public LibraryService(Connection connection) throws SQLException {
        this.connection = connection;
        createLibrariesTableIfNotExists();
    }

    public List<Library> getAllLibraries() throws SQLException {
        List<Library> libraries = new ArrayList<>();
        String query = "SELECT * FROM libraries";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");


                libraries.add(new Library(id, name));
            }
        }
        return libraries;
    }

    public boolean addLibrary(AddLibraryDTO library) throws SQLException {
        String query = "INSERT INTO libraries (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, library.getName());


            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateLibrary(UpdateLibraryDTO library) throws SQLException {
        String query = "UPDATE libraries SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, library.getLibraryName());


            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLibrary(long id) throws SQLException {
        String query = "DELETE FROM libraries WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createLibrariesTableIfNotExists() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS libraries ("
                + "id SERIAL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL"
                + ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }


}
