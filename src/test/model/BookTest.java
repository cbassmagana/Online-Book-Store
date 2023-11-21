package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for book class
public class BookTest {
    Book b1;

    @BeforeEach
    public void runBefore() {
        b1 = new Book("Harry Potter", "JK Rowling", 10.99);
    }

    @Test
    public void testConstructorAndSetters() {
        assertEquals("Harry Potter", b1.getTitle());
        assertEquals("JK Rowling", b1.getAuthor());
        assertFalse(b1.getReservedStatus());
        assertEquals(10.99, b1.getPrice());
        List<Integer> ratings = new ArrayList<>();
        assertEquals(ratings, b1.getRatings());
        b1.setAuthor("abc");
        b1.setTitle("def");
        assertEquals("abc", b1.getAuthor());
        assertEquals("def", b1.getTitle());
    }

    @Test
    public void testRateBook() {
        assertEquals(0.0, b1.getAverageRating());
        assertEquals(3, b1.rateBook(3));
        assertEquals(2, b1.rateBook(1));
        assertEquals(3, b1.rateBook(5));
        assertEquals(2.5, b1.rateBook(1));
        assertEquals(2.5, b1.getAverageRating());
    }

    @Test
    public void testSetPrice() {
        b1.setPrice(9.99);
        assertEquals(9.99, b1.getPrice());
        b1.setPrice(8.8);
        assertEquals(8.80, b1.getPrice());
        b1.setPrice(12.2228976);
        assertEquals(12.22, b1.getPrice());
        b1.setPrice(14.229999);
        assertEquals(14.22, b1.getPrice());
    }
}
