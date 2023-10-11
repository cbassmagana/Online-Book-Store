package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for branch class
public class BranchTest {
    Branch br1;
    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    public void runBefore() {
        br1 = new Branch("Downtown", "123 Apple Rd");
        b1 = new Book("Harry Potter", "JK Rowling", 10.99);
        b2 = new Book("Percy Jackson", "Rick Riordan", 9.99);
        b3 = new Book("Cat in the Hat", "Dr Suess", 7.99);
    }

    @Test
    public void testConstructor() {
        assertEquals("Downtown", br1.getName());
        assertEquals("123 Apple Rd", br1.getAddress());
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        assertEquals(emptyListBooks, br1.getInventory());
        assertEquals(emptyListBooks, br1.getBooksSold());
    }

    @Test
    public void testAddBookOnce() {
        br1.addBook(b1);
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        emptyListBooks.add(b1);
        assertEquals(emptyListBooks, br1.getInventory());
    }

    @Test
    public void testAddBookManyTimes() {
        br1.addBook(b1);
        br1.addBook(b2);
        br1.addBook(b3);
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        emptyListBooks.add(b1);
        emptyListBooks.add(b2);
        emptyListBooks.add(b3);
        assertEquals(emptyListBooks, br1.getInventory());
    }

    @Test
    public void testRemoveBook() {
        br1.addBook(b1);
        br1.addBook(b2);
        br1.addBook(b3);
        br1.removeBook(b2);
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        emptyListBooks.add(b1);
        emptyListBooks.add(b3);
        assertEquals(emptyListBooks, br1.getInventory());
        br1.removeBook(b3);
        emptyListBooks.remove(1);
        assertEquals(emptyListBooks, br1.getInventory());
    }

    @Test
    public void testSellBookOnce() {
        br1.addBook(b1);
        br1.sellBook(b1);
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        assertEquals(emptyListBooks, br1.getInventory());
        emptyListBooks.add(b1);
        assertEquals(emptyListBooks, br1.getBooksSold());
    }

    @Test
    public void testSellBookReserved() {
        br1.addBook(b1);
        b1.setReservedStatus(true);
        br1.sellBook(b1);
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        assertEquals(emptyListBooks, br1.getBooksSold());
        emptyListBooks.add(b1);
        assertEquals(emptyListBooks, br1.getInventory());
    }

    @Test
    public void testSellBookMultiple() {
        br1.addBook(b1);
        br1.addBook(b2);
        br1.addBook(b3);
        br1.sellBook(b3);
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        emptyListBooks.add(b1);
        emptyListBooks.add(b2);
        ArrayList<Book> noBooksSold = new ArrayList<>();
        noBooksSold.add(b3);
        assertEquals(emptyListBooks, br1.getInventory());
        assertEquals(noBooksSold, br1.getBooksSold());
        br1.sellBook(b1);
        emptyListBooks.remove(0);
        noBooksSold.add(b1);
        assertEquals(emptyListBooks, br1.getInventory());
        assertEquals(noBooksSold, br1.getBooksSold());
    }

    @Test
    public void testGetInventoryValue() {
        br1.addBook(b1);
        br1.addBook(b2);
        br1.addBook(b3);
        assertEquals(28.97, br1.getInventoryValue());
    }

    @Test
    public void testGetCashValue() {
        br1.addBook(b1);
        br1.addBook(b2);
        br1.addBook(b3);
        br1.sellBook(b1);
        br1.sellBook(b2);
        assertEquals(20.98, br1.getCashValue());
    }
}
