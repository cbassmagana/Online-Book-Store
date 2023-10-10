package model;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private final String name;
    private final String address;
    private List<Book> inventory;
    private List<Book> booksSold;

    public Branch(String name, String address) {
        this.name = name;
        this.address = address;
        this.inventory = new ArrayList<>();
        this.booksSold = new ArrayList<>();
    }

    public void addBook(Book b) {
        this.inventory.add(b);
    }

    public void removeBook(Book b) {
        this.inventory.remove(b);
    }

    public void sellBook(Book b) {
        if (!b.getReservedStatus()) {
            this.inventory.remove(b);
            this.booksSold.add(b);
        }
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

    public double getInventoryValue() {
        double inventoryValue = 0;
        for (Book b : this.inventory) {
            inventoryValue += b.getPrice();
        }
        return inventoryValue;
    }

    public double getCashBalance() {
        double cashValue = 0;
        for (Book b : this.booksSold) {
            cashValue += b.getPrice();
        }
        return cashValue;
    }
}
