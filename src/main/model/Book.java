package model;

import java.util.List;
import java.util.ArrayList;

public class Book {
    private final String title;
    private final String author;
    private double price;
    private List<Integer> ratings;
    private boolean reservedStatus;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.ratings = new ArrayList<>();
        this.reservedStatus = false;
    }

    public double rateBook(int rating) {
        this.ratings.add(rating);
        return getAverageRating();
    }

    public double getAverageRating() {
        double sumOfRatings = 0;
        double numberOfRatings = 0;
        for (int r: this.ratings) {
            sumOfRatings += r;
            numberOfRatings += 1;
        }
        return sumOfRatings / numberOfRatings;
    }

    public void setReservedStatus(boolean status) {
        this.reservedStatus = status;
    }

    public void setPrice(double price) {
        this.price = price;
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
