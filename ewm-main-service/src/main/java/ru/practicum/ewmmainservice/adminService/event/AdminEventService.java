package ru.practicum.ewmmainservice.adminService.event;

import ru.practicum.ewmmainservice.exceptions.EventStatusException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;

import java.util.Collection;

public interface AdminEventService {
    Collection<Event> findByCategoryId(Long catId);

    EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) throws NotFoundException;

    EventFullDto publishEvent(Long eventId) throws NotFoundException, IllegalTimeException, EventStatusException;

    EventFullDto rejectEvent(Long eventId) throws NotFoundException, EventStatusException, IllegalTimeException;
    Event findById(Long eventId) throws NotFoundException;

    void save(Event event);
}
