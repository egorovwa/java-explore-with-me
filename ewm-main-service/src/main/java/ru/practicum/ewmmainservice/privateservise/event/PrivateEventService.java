package ru.practicum.ewmmainservice.privateservise.event;

import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.event.dto.UpdateEventRequest;

public interface PrivateEventService {
    EventFullDto createEvent(Long userId, NewEventDto newEventDto) throws FiledParamNotFoundException;

    EventFullDto patchEvent(Long userId, UpdateEventRequest requestEvent) throws StatusException, IllegalTimeException, NotFoundException, IlegalUserIdException;

    EventFullDto eventСancellation(Long userId, Long eventId) throws NotFoundException, StatusException;
}
