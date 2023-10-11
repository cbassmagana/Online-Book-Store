package model;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

// A representation of a book with a title, author, price, reserved status
// and a list of ratings
public class Book {
    private final String title;
    private final String author;
    private double price;
    private List<Integer> ratings;
    private boolean reservedStatus;

    // REQUIRES: title and author are non-empty strings, price is to 2 decimals
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
    // EFFECTS: adds a rating to the list of ratings for this book and returns the
    //          new average rating
    public double rateBook(int rating) {
        this.ratings.add(rating);
        return getAverageRating();
    }

    // REQUIRES: this.ratings is a non-empty list
    // EFFECTS: returns the average rating of this book
    public double getAverageRating() {
        double sumOfRatings = 0;
        double numberOfRatings = 0;
        for (int r: this.ratings) {
            sumOfRatings += r;
            numberOfRatings += 1;
        }
        double averageRating = sumOfRatings / numberOfRatings;
        return Math.floor(averageRating * 10) / 10;
    }

    public void setReservedStatus(boolean status) {
        this.reservedStatus = status;
    }

    public void setPrice(double price) {
        this.price = Math.floor(price * 10) / 10;;
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
}
