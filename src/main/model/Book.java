package model;

public class Book {
    private String title;
    private String author;
    private double price;
    private int rating;
    private boolean reservedStatus;

    public Book(String title, String author, double price, int rating) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.rating = rating;
        this.reservedStatus = false;
    }



    public void setReservedStatus(boolean status) {
        this.reservedStatus = status;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public int getRating() {
        return this.rating;
    }

    public boolean getReservedStatus() {
        return this.reservedStatus;
    }
}
