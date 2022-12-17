package ru.practicum.ewmmainservice.privateservise.event;



import com.example.evmdtocontract.dto.event.EventFullDto;
import com.example.evmdtocontract.dto.event.EventShortDto;
import com.example.evmdtocontract.dto.event.NewEventDto;
import com.example.evmdtocontract.dto.event.UpdateEventRequest;
import ru.practicum.ewmmainservice.exceptions.*;

import java.util.List;

public interface PrivateEventService {
    EventFullDto createEvent(Long userId, NewEventDto newEventDto) throws FiledParamNotFoundException;

    EventFullDto patchEvent(Long userId, UpdateEventRequest requestEvent) throws StatusException, IllegalTimeException, NotFoundException, IlegalUserIdException;

    EventFullDto eventСancellation(Long userId, Long eventId) throws NotFoundException, StatusException;

    EventFullDto findEventForInitiator(Long userId, Long eventId) throws NotFoundException, IlegalUserIdException;

    List<EventShortDto> findAllEventByInitiator(Long userId) throws NotFoundException;
}
