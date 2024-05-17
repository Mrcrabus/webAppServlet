package org.example.webappservlet.books.services;

import org.example.webappservlet.books.dto.AddBookKeeperDTO;
import org.example.webappservlet.model.Book;
import org.example.webappservlet.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final Connection connection;

    public BookService() throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        createBooksTableIfNotExists();
    }

    public BookService(Connection connection) throws SQLException {
        this.connection = connection;
        createBooksTableIfNotExists();
    }

    public Book getBookById(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                Integer userId = resultSet.getInt("user_id");

                if (resultSet.wasNull()) {
                    userId = null;
                }
                book = new Book(id, title, author, year, userId);
            }
            return book;
        }
    }


    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                Integer userId = resultSet.getInt("user_id");

                if (resultSet.wasNull()) {
                    userId = null;
                }

                books.add(new Book(id, title, author, year, userId));
            }
        }
        return books;
    }

    public boolean addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, year, user_id) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYear());
            statement.setNull(4, Types.NULL);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBookToUser(AddBookKeeperDTO bookKeeperDTO) throws SQLException {
        String query = "UPDATE books SET user_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bookKeeperDTO.getUserId());
            statement.setLong(2, bookKeeperDTO.getBookId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeUserFromBook(Integer bookId) throws SQLException {
        String query = "UPDATE books SET user_id = NULL WHERE id = ?";
        if (this.getBookById(bookId) == null) {
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateBook(Book book) throws SQLException {
        String query = "UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYear());
            statement.setLong(4, book.getId());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBook(long id) throws SQLException {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createBooksTableIfNotExists() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS books ("
                + "id SERIAL PRIMARY KEY,"
                + "title VARCHAR(255) NOT NULL,"
                + "author VARCHAR(255) NOT NULL,"
                + "year INT NOT NULL"
                + ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

}
