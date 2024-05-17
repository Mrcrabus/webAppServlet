import org.example.webappservlet.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookTest {

    @Test
    void constructorWithTitleAuthorAndYear_CreatesBookObjectWithSpecifiedValues() {
        // Arrange
        String title = "Test Title";
        String author = "Test Author";
        int year = 2022;

        // Act
        Book book = new Book(title, author, year);

        // Assert
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(year, book.getYear());
        assertNull(book.getId());
        assertNull(book.getUserId());
    }

    @Test
    void constructorWithIdTitleAuthorYearAndUserId_CreatesBookObjectWithSpecifiedValues() {
        // Arrange
        long id = 1;
        String title = "Test Title";
        String author = "Test Author";
        int year = 2022;
        Integer userId = 123;

        // Act
        Book book = new Book(id, title, author, year, userId);

        // Assert
        assertEquals(id, book.getId());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(year, book.getYear());
        assertEquals(userId, book.getUserId());
    }

    @Test
    void getUserId_ReturnsUserId() {
        // Arrange
        Integer userId = 123;
        Book book = new Book();
        book.setUserId(userId);

        // Act
        int retrievedUserId = book.getUserId();

        // Assert
        assertEquals(userId, retrievedUserId);
    }
}
