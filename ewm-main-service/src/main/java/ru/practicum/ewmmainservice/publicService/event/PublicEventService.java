package ru.practicum.ewmmainservice.publicService.event;

import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;

import java.util.Collection;

public interface PublicEventService {
    Collection<EventShortDto> findEfents();
}
