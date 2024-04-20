package org.example.webappservlet.utils;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties props = new Properties();
                InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties");
                props.load(inputStream);

                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, username, password);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                throw new SQLException("Error connecting to database", e);
            }
        }
        return connection;
    }
}
