package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

// a representation of a branch of a book store company.
public class Branch implements Writable {
    private final String name;
    private final String address;
    private List<Book> inventory;
    private List<Book> booksSold;

    // EFFECTS: instantiates a branch with a given name and address, an empty inventory
    //          and empty list of books sold
    public Branch(String name, String address) {
        this.name = name;
        this.address = address;
        this.inventory = new ArrayList<>();
        this.booksSold = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a given book to the inventory of this branch
    public void addBook(Book b) {
        this.inventory.add(b);
    }

    // REQUIRES: book b is in this branch's inventory
    // MODIFIES: this
    // EFFECTS: removes a given book from the inventory of this branch
    public void removeBook(Book b) {
        this.inventory.remove(b);
    }

    // REQUIRES: book b is in this branch's inventory
    // MODIFIES: this
    // EFFECTS: removes a given book from this inventory and adds it to the
    //          list of books sold
    public void sellBook(Book b) {
        if (!b.getReservedStatus()) {
            this.inventory.remove(b);
            this.booksSold.add(b);
        }
    }

    // EFFECTS: returns the value (in dollars) of all the books in the inventory (rounded to 2 decimals)
    public double getInventoryValue() {
        double inventoryValue = 0;
        for (Book b : this.inventory) {
            inventoryValue += b.getPrice();
        }
        return Math.floor(inventoryValue * 100) / 100;
    }

    // EFFECTS: returns the value (in dollars) of all the books that have been sold
    //          (rounded to 2 decimals)
    public double getCashValue() {
        double cashValue = 0;
        for (Book b : this.booksSold) {
            cashValue += b.getPrice();
        }
        return Math.floor(cashValue * 100) / 100;
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

    @Override
    // EFFECTS: returns this branch as a JSON object to be stored
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("address", address);
        json.put("inventory", inventoryToJson());
        json.put("sales", salesToJson());
        return json;
    }

    // EFFECTS: returns this branches' list of inventory books as a JSON array
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b : inventory) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns this branches' list of books sold as a JSON array
    private JSONArray salesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b : booksSold) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}
