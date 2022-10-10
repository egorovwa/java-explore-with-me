package ru.practicum.ewmmainservice.models.event;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventStateTest {

    @Test
    void from_PENDING() {
        EventState eventState = EventState.from("pENDING").get();
        assertEquals(eventState, EventState.PENDING);
    }

    @Test
    void from_PUBLISHED() {
        EventState eventState = EventState.from("PUBLIsHED").get();
        assertEquals(eventState, EventState.PUBLISHED);
    }

    @Test
    void from_CANCELED() {
        EventState eventState = EventState.from("CANCELEd").get();
        assertEquals(eventState, EventState.CANCELED);
    }

    @Test
    void from_incorrect() {
        Optional<EventState> eventState = EventState.from("dssd");
        assertTrue(eventState.isEmpty());
    }
}