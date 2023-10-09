package model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private List<Branch> stores;


    public Company (String name) {
        this.name = name;
        this.stores = new ArrayList<>();
    }


    public void addBranch(Branch br) {
        this.stores.add(br);
    }

    public void closeBranch(Branch br) {
        this.stores.remove(br);
    }


    public List<Book> getInventory() {
        List<Book> totalInventory = new ArrayList<>();

        for (Branch br: stores) {
            for (Book b : br.getInventory()) {
                totalInventory.add(b);
            }
        }
        return totalInventory;
    }

    public List<Book> getBooksSold() {
        List<Book> totalBooksSold = new ArrayList<>();

        for (Branch br: stores) {
            for (Book b : br.getBooksSold()) {
                totalBooksSold.add(b);
            }
        }
        return totalBooksSold;
    }


    public double getInventoryValue() {
        double totalInventoryValue = 0;

        for (Branch br: stores) {
            totalInventoryValue += br.getInventoryValue();
        }
        return totalInventoryValue;
    }

    public double getCashValue() {
        double totalCashValue = 0;

        for (Branch br: stores) {
            totalCashValue += br.getCashBalance();
        }
        return totalCashValue;
    }


    public String getName() {
        return this.name;
    }

    public List<Branch> getStores() {
        return this.stores;
    }
}
