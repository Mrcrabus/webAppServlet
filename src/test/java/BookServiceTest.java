import org.example.webappservlet.books.services.BookService;
import org.example.webappservlet.model.Book;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private BookService bookService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        bookService = new BookService(connection);
    }

    @Test
    void getAllBooks_ReturnsCorrectBooks() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("title")).thenReturn("Test Book");
        when(resultSet.getString("author")).thenReturn("Test Author");
        when(resultSet.getInt("year")).thenReturn(2022);
        when(resultSet.getInt("user_id")).thenReturn(1);


        List<Book> books = bookService.getAllBooks();

        assertEquals(1, books.size());
        assertEquals(1, books.get(0).getId());
        assertEquals("Test Book", books.get(0).getTitle());
        assertEquals("Test Author", books.get(0).getAuthor());
        assertEquals(2022, books.get(0).getYear());
        assertEquals(1, books.get(0).getUserId());
    }

    @Test
    void addBook_ReturnsTrueWhenBookAddedSuccessfully() throws SQLException {
        Book book = new Book(1,"Test Book", "Test Author", 2022, null);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = bookService.addBook(book);

        assertEquals(true, result);
    }

    @Test
    void addBook_ReturnsFalseWhenBookNotAddedSuccessfully() throws SQLException {
        Book book = new Book(1,"Test Book", "Test Author", 2022, null);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = bookService.addBook(book);


        assertFalse(result);
    }

}
