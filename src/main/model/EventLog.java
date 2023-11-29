package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// A representation of a log of events within the book store company
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: instantiates the event log as an empty list
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: returns the singleton instance of EventLog to be used globally
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the list of events in event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: removes all events from event log and logs that event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns specific iterator for EventLog to iterate over events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}

