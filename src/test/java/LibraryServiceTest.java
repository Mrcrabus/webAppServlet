import org.example.webappservlet.libraries.dto.AddLibraryDTO;
import org.example.webappservlet.libraries.dto.UpdateLibraryDTO;
import org.example.webappservlet.libraries.services.LibraryService;
import org.example.webappservlet.model.Library;
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
import static org.mockito.Mockito.when;

public class LibraryServiceTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        libraryService = new LibraryService(connection);
    }

    @Test
    void testGetAllLibraries_ReturnsCorrectLibraries() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Test Library");

        List<Library> libraries = libraryService.getAllLibraries();

        assertEquals(1, libraries.size());
        assertEquals(1, libraries.get(0).getId());
        assertEquals("Test Library", libraries.get(0).getName());
    }

    @Test
    void testAddLibrary_ReturnsTrueWhenLibraryAddedSuccessfully() throws SQLException {
        AddLibraryDTO library = new AddLibraryDTO();

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = libraryService.addLibrary(library);

        assertTrue(result);
    }

    @Test
    void testAddLibrary_ReturnsFalseWhenLibraryNotAddedSuccessfully() throws SQLException {
        AddLibraryDTO library = new AddLibraryDTO();

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = libraryService.addLibrary(library);

        assertFalse(result);
    }

    @Test
    void testUpdateLibrary_ReturnsTrueWhenLibraryUpdatedSuccessfully() throws SQLException {
        UpdateLibraryDTO library = new UpdateLibraryDTO();

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = libraryService.updateLibrary(library);

        assertTrue(result);
    }

    @Test
    void testUpdateLibrary_ReturnsFalseWhenLibraryNotUpdatedSuccessfully() throws SQLException {
        UpdateLibraryDTO library = new UpdateLibraryDTO();

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = libraryService.updateLibrary(library);

        assertFalse(result);
    }

    @Test
    void testDeleteLibrary_ReturnsTrueWhenLibraryDeletedSuccessfully() throws SQLException {
        long id = 1;

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = libraryService.deleteLibrary(id);

        assertTrue(result);
    }

    @Test
    void testDeleteLibrary_ReturnsFalseWhenLibraryNotDeletedSuccessfully() throws SQLException {
        long id = 1;

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = libraryService.deleteLibrary(id);

        assertFalse(result);
    }
}
