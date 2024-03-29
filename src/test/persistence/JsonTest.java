package persistence;

import model.Branch;
import model.Book;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

// Used in subclasses' tests for JSON to compare JSONObjects to Books and Branches
public class JsonTest {

    protected void checkBook(String title, String author, double price, boolean reserved,
                             ArrayList<Integer> ratings, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(price, book.getPrice());
        assertEquals(reserved, book.getReservedStatus());
        assertEquals(ratings, book.getRatings());
    }

    protected void checkBranch(String name, String address, Branch branch) {
        assertEquals(name, branch.getName());
        assertEquals(address, branch.getAddress());
    }
}
