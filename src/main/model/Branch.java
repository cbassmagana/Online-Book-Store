package model;

import java.util.ArrayList;
import java.util.List;

// a representation of a store branch with a list of books sold and still in the store.
public class Branch {
    private final String name;
    private final String address;
    private List<Book> inventory;
    private List<Book> booksSold;

    // REQUIRES: 0 <= rating <= 5
    // EFFECTS: instantiates a branch with a name, address, empty inventory
    //          and empty list of books sold
    public Branch(String name, String address) {
        this.name = name;
        this.address = address;
        this.inventory = new ArrayList<>();
        this.booksSold = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a book to the inventory of this branch
    public void addBook(Book b) {
        this.inventory.add(b);
    }

    // REQUIRES: book b is in this branch's inventory
    // MODIFIES: this
    // EFFECTS: removes a book from the inventory of this branch
    public void removeBook(Book b) {
        this.inventory.remove(b);
    }

    // REQUIRES: book b is in this branch's inventory
    // MODIFIES: this
    // EFFECTS: removes a book from this inventory and adds it to the
    //          list of books sold
    public void sellBook(Book b) {
        if (!b.getReservedStatus()) {
            this.inventory.remove(b);
            this.booksSold.add(b);
        }
    }

    // EFFECTS: returns the value (in dollars) of all the books in the inventory
    public double getInventoryValue() {
        double inventoryValue = 0;
        for (Book b : this.inventory) {
            inventoryValue += b.getPrice();
        }
        return inventoryValue;
    }

    // EFFECTS: returns the value (in dollars) of all the books that have been sold
    public double getCashValue() {
        double cashValue = 0;
        for (Book b : this.booksSold) {
            cashValue += b.getPrice();
        }
        return cashValue;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public List<Book> getInventory() {
        return this.inventory;
    }

    public List<Book> getBooksSold() {
        return this.booksSold;
    }
}
