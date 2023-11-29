package model;

import java.util.Calendar;
import java.util.Date;


// A representation of an event occurring in the book company
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    // Instantiates the Event with the given description and current time
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    public Date getDate() {
        return dateLogged;
    }

    public String getDescription() {
        return description;
    }

    // EFFECTS: Overrides equals to compare two events based on their dates and descriptions
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;
        return (this.dateLogged.equals(otherEvent.dateLogged) && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: Creates and returns string in format printed to the console
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}

