package ru.practicum.ewmmainservice.adminService.event;

import ru.practicum.ewmmainservice.models.event.Event;

import java.util.Collection;

public interface EventService {
    Collection<Event> findByCategoryId(Long catId);
}
