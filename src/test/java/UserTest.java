import org.example.webappservlet.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    @Test
    void testGettersAndSetters() {

        int expectedId = 1;
        String expectedName = "John";

        User user = new User(expectedId, expectedName);

        assertEquals(expectedId, user.getId());
        assertEquals(expectedName, user.getUsername());
    }
}