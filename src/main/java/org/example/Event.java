package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private String description;
    private LocalDateTime time;

    public Event(String description, LocalDateTime time) {
        this.description = description;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(description, event.description) && Objects.equals(time, event.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, time);
    }

    @Override
    public String toString() {
        return "Event{" +
                "description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
