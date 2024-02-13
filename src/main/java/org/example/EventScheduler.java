package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventScheduler {
    private List<Event> events;

    public EventScheduler() {
        this.events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public Event deleteEvent(int number) {
        return events.remove(number);
    }

    public List<Event> filterByDate(LocalDate date) {
        return events
                .stream()
                .filter(event -> event.getTime().toLocalDate().equals(date))
                .toList();
    }
}
