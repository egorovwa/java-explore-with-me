package ru.practicum.ewmmainservice.publicservice.event;

import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;

import java.util.Collection;

public interface PublicEventService {
    Collection<EventShortDto> findEvents(ParametersPublicEventFind param);

    EventFullDto findById(Long id, String requestURI, String remoteAddr) throws NotFoundException;
}
