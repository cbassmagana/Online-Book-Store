package persistence;

import model.Company;
import model.Branch;
import model.Book;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Contains methods to be used in subclasses' tests for JSON
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
