package ru.practicum.ewmmainservice.publicservice.event;

import com.example.evmdtocontract.dto.event.EventFullDto;
import com.example.evmdtocontract.dto.event.EventShortDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;

import java.util.Collection;

public interface PublicEventService {
    Collection<EventShortDto> findEvents(ParametersPublicEventFind param);

    EventFullDto findById(Long id, String requestURI, String remoteAddr) throws NotFoundException;
}
