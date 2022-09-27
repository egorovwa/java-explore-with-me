package ru.practicum.ewmmainservice.publicService.event;

import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;

import java.util.Collection;

public interface PublicEventService {
    Collection<EventShortDto> findEfents(ParametersPublicEventFind param);
}
