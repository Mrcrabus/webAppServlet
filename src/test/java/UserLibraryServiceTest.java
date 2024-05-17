import org.example.webappservlet.model.UserLibrary;
import org.example.webappservlet.userLibrary.dto.AddUserToLibraryDTO;
import org.example.webappservlet.userLibrary.services.UserLibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserLibraryServiceTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private UserLibraryService userLibraryService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        userLibraryService = new UserLibraryService(connection);
    }

    @Test
    void getAllUserLibrary_ReturnsCorrectUserLibraries() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("user_id")).thenReturn("1");
        when(resultSet.getString("library_id")).thenReturn("1");

        List<UserLibrary> userLibraries = userLibraryService.getAllUserLibrary();

        assertEquals(1, userLibraries.size());
        assertEquals("1", userLibraries.get(0).getUserId());
        assertEquals("1", userLibraries.get(0).getLibraryId());
    }




    @Test
    void addUserToLibrary_ReturnsTrueWhenAddedSuccessfully() throws SQLException {

        AddUserToLibraryDTO addUserToLibraryDTO = new AddUserToLibraryDTO();
        addUserToLibraryDTO.userId = 1; // Set userId to a non-null value
        addUserToLibraryDTO.libraryId = 101;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userLibraryService.addUserToLibrary(addUserToLibraryDTO);

        assertEquals(true, result);
    }


    @Test
    void addUserToLibrary_ReturnsFalseWhenNotAddedSuccessfully() throws SQLException {

        AddUserToLibraryDTO addUserToLibraryDTO = new AddUserToLibraryDTO();

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = userLibraryService.addUserToLibrary(addUserToLibraryDTO);

        assertEquals(false, result);
    }
}
