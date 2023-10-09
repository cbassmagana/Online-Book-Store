package model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private List<Branch> stores;
    private List<Book> inventory;
    private List<Book> booksSold;


    public Company (String name) {
        this.name = name;
        this.stores = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.booksSold = new ArrayList<>();
    }





    public String getName() {
        return this.name;
    }

    public List<Branch> getStores() {
        return this.stores;
    }

    public List<Book> getInventory() {
        return this.inventory;
    }

    public List<Book> getBooksSold() {
        return this.booksSold;
    }
}
