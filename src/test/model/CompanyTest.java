package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
    Company company;
    Branch br1;
    Branch br2;
    Branch br3;
    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    public void runBefore() {
        company = new Company("Bob's Book Stores");
        br1 = new Branch("Downtown", "123 Apple Rd");
        br2 = new Branch("Waterfront", "321 Banana St");
        br3 = new Branch("University", "789 Dogwood Ave");
        b1 = new Book("Harry Potter", "JK Rowling", 10.99, 5);
        b2 = new Book("Percy Jackson", "Rick Riordan", 9.99, 4);
        b3 = new Book("Cat in the Hat", "Dr Suess", 7.99, 3);
    }

    @Test
    public void testConstructor() {
        assertEquals("Bob's Book Stores", company.getName());
        assertTrue(company.getStores().isEmpty());
        assertEquals(0, company.getInventoryValue());
        assertEquals(0, company.getCashValue());
        ArrayList<Book> emptyListBooks = new ArrayList<>();
        assertEquals(emptyListBooks, company.getInventory());
        assertEquals(emptyListBooks, company.getBooksSold());
    }

    @Test
    public void testAddBranchOnce() {
        company.addBranch(br1);
        ArrayList<Branch> answer = new ArrayList<>();
        answer.add(br1);
        assertEquals(answer, company.getStores());
    }

    @Test
    public void testAddBranchMany() {
        company.addBranch(br1);
        company.addBranch(br2);
        company.addBranch(br3);
        ArrayList<Branch> answer = new ArrayList<>();
        answer.add(br1);
        answer.add(br2);
        answer.add(br3);
        assertEquals(answer, company.getStores());
    }

    @Test
    public void testCloseBranches() {
        company.addBranch(br1);
        company.addBranch(br2);
        company.addBranch(br3);
        company.closeBranch(br2);
        ArrayList<Branch> answer = new ArrayList<>();
        answer.add(br1);
        answer.add(br3);
        assertEquals(answer, company.getStores());
        company.closeBranch(br1);
        answer.remove(0);
        assertEquals(answer, company.getStores());
    }

    @Test
    public void testGetInventory() {
        company.addBranch(br1);
        company.addBranch(br2);
        company.addBranch(br3);
        br1.addBook(b1);
        br2.addBook(b2);
        br2.addBook(b3);
        ArrayList<Book> answer = new ArrayList<>();
        answer.add(b1);
        answer.add(b2);
        answer.add(b3);
        assertEquals(answer, company.getInventory());
    }

    @Test
    public void testGetBooksSold() {
        company.addBranch(br1);
        company.addBranch(br2);
        company.addBranch(br3);
        br1.addBook(b1);
        br2.addBook(b2);
        br2.addBook(b3);
        br2.sellBook(b2);
        ArrayList<Book> answer = new ArrayList<>();
        answer.add(b2);
        assertEquals(answer, company.getBooksSold());
    }

    @Test
    public void testGetInventoryValue() {
        company.addBranch(br1);
        company.addBranch(br2);
        company.addBranch(br3);
        br1.addBook(b1);
        br2.addBook(b2);
        br2.addBook(b3);
        assertEquals(28.97, company.getInventoryValue());
    }

    @Test
    public void testGetCashValue() {
        company.addBranch(br1);
        company.addBranch(br2);
        company.addBranch(br3);
        br1.addBook(b1);
        br2.addBook(b2);
        br2.addBook(b3);
        br2.sellBook(b2);
        assertEquals(9.99, company.getCashValue());
    }
}