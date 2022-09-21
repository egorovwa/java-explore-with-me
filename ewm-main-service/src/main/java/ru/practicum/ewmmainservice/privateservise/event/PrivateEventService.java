package ru.practicum.ewmmainservice.privateservise.event;

import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;

public interface PrivateEventService {
    EventFullDto createEvent(Long userId, NewEventDto newEventDto) throws FiledParamNotFoundException;
}
