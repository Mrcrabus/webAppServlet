import org.example.webappservlet.model.User;
import org.example.webappservlet.users.dto.AddUserDTO;
import org.example.webappservlet.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private UserService userService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        userService = new UserService(connection);
    }


    @Test
    void getAllUsers_ReturnsCorrectUsers() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("username")).thenReturn("test");

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("test", users.get(0).getUsername());
    }

    @Test
    void addUser_ReturnsTrueWhenUserAddedSuccessfully() throws SQLException {
        AddUserDTO user = new AddUserDTO();

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userService.addUser(user);

        assertTrue(result);
    }

    @Test
    void addUser_ReturnsFalseWhenUserNotAddedSuccessfully() throws SQLException {

        AddUserDTO user = new AddUserDTO();

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = userService.addUser(user);

        assertFalse(result);
    }
}
