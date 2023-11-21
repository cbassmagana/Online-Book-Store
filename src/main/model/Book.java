package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

// A representation of a book
public class Book implements Writable {
    private String title;
    private String author;
    private double price;
    private List<Integer> ratings;
    private boolean reservedStatus;

    // EFFECTS: instantiates a book with a title, author, price, empty list of
    //          ratings and a not reserved status.
    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.ratings = new ArrayList<>();
        this.reservedStatus = false;
    }

    // REQUIRES: 0 <= rating <= 5
    // MODIFIES: this
    // EFFECTS: adds a given rating to the list of ratings for this book and returns the
    //          new average rating
    public double rateBook(int rating) {
        this.ratings.add(rating);
        return getAverageRating();
    }

    // EFFECTS: returns the average rating of this book
    public double getAverageRating() {
        double sumOfRatings = 0;
        double numberOfRatings = 0;

        if (this.ratings.size() == 0) {
            return 0.0;
        }

        for (int r: this.ratings) {
            sumOfRatings += r;
            numberOfRatings += 1;
        }
        double averageRating = sumOfRatings / numberOfRatings;
        return Math.floor(averageRating * 10) / 10;
    }

    // EFFECTS: Sets this book's price to a given price (rounded down to 2 decimal points)
    public void setPrice(double price) {
        this.price = Math.floor(price * 100) / 100;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public void setReservedStatus(boolean status) {
        this.reservedStatus = status;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public double getPrice() {
        return this.price;
    }

    public boolean getReservedStatus() {
        return this.reservedStatus;
    }

    public List<Integer> getRatings() {
        return this.ratings;
    }

    @Override
    // EFFECTS: returns this book as a JSON object to be stored
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("price", price);
        json.put("reserved", reservedStatus);
        json.put("ratings", ratingsToJson());
        return json;
    }

    // EFFECTS: returns this book's ratings as a JSON array
    private JSONArray ratingsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int r : ratings) {
            jsonArray.put(r);
        }
        return jsonArray;
    }
}
