package model;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String name;
    private String address;
    private List<Book> inventory;
    private List<Book> booksSold;
    private double inventoryValue;
    private double cashBalance;

    public Branch (String name, String address) {
        this.name = name;
        this.address = address;
        this.inventory = new ArrayList<>();
        this.booksSold = new ArrayList<>();
        this.inventoryValue = 0;
        this.cashBalance = 0;
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
        return this.inventoryValue;
    }

    public double getCashBalance() {
        return this.cashBalance;
    }
}
