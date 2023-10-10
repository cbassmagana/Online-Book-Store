package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookTest {
    Book b1;

    @BeforeEach
    public void runBefore() {
        b1 = new Book("Harry Potter", "JK Rowling", 10.99);
    }

    @Test
    public void testConstructor() {
        assertEquals("Harry Potter", b1.getTitle());
        assertEquals("JK Rowling", b1.getAuthor());
        assertFalse(b1.getReservedStatus());
        assertEquals(10.99, b1.getPrice());
        List<Integer> ratings = new ArrayList<>();
        assertEquals(ratings, b1.getRatings());
    }

    @Test
    public void testRateBook() {
        assertEquals(3, b1.rateBook(3));
        assertEquals(2, b1.rateBook(1));
        assertEquals(3, b1.rateBook(5));
        assertEquals(2.5, b1.rateBook(1));
        assertEquals(2.5, b1.getAverageRating());
    }
}
