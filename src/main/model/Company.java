package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// A representation of a book store company with a name and various branches
public class Company implements Writable {
    private final String name;
    private List<Branch> branches;

    // EFFECTS: instantiates a company with a given name and an empty list of branches
    public Company(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds given branch to the list of branches
    public void addBranch(Branch br) {
        this.branches.add(br);
    }

    // MODIFIES: this
    // EFFECTS: removes given branch from the list of branches
    public void closeBranch(Branch br) {
        this.branches.remove(br);
    }

    // EFFECTS: returns a list of all the books in any of the company's branches' inventories
    public List<Book> getInventory() {
        List<Book> totalInventory = new ArrayList<>();

        for (Branch br: branches) {
            for (Book b : br.getInventory()) {
                totalInventory.add(b);
            }
        }
        return totalInventory;
    }

    // EFFECTS: returns a list of all the books sold at any of the company's branches
    public List<Book> getBooksSold() {
        List<Book> totalBooksSold = new ArrayList<>();

        for (Branch br: branches) {
            for (Book b : br.getBooksSold()) {
                totalBooksSold.add(b);
            }
        }
        return totalBooksSold;
    }

    // EFFECTS: returns the value (in dollars) of all the books in the company's branches
    public double getInventoryValue() {
        double totalInventoryValue = 0;

        for (Branch br: branches) {
            totalInventoryValue += br.getInventoryValue();
        }
        return totalInventoryValue;
    }

    // EFFECTS: returns the value (in dollars) of all the books sold at the company's branches
    public double getCashValue() {
        double totalCashValue = 0;

        for (Branch br: branches) {
            totalCashValue += br.getCashValue();
        }
        return totalCashValue;
    }

    public String getName() {
        return this.name;
    }

    public List<Branch> getBranches() {
        return this.branches;
    }

    @Override
    // EFFECTS: stores the company as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("branches", branchesToJson());
        return json;
    }

    // EFFECTS: returns the company's branches as a JSON array
    private JSONArray branchesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Branch br : branches) {
            jsonArray.put(br.toJson());
        }
        return jsonArray;
    }
}
