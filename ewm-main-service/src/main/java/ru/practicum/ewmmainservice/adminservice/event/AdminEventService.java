package ru.practicum.ewmmainservice.adminservice.event;

import com.example.evmdtocontract.dto.event.AdminUpdateEventRequest;
import com.example.evmdtocontract.dto.event.EventFullDto;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.parameters.ParametersAdminFindEvent;

import java.util.Collection;
import java.util.List;

public interface AdminEventService {
    Collection<Event> findByCategoryId(Long catId);

    EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) throws NotFoundException, FiledParamNotFoundException;

    EventFullDto publishEvent(Long eventId) throws NotFoundException, IllegalTimeException, StatusException;

    EventFullDto rejectEvent(Long eventId) throws NotFoundException, StatusException, IllegalTimeException;

    Event findById(Long eventId) throws NotFoundException;

    void save(Event event);

    List<EventFullDto> findAllEvents(ParametersAdminFindEvent parameters) throws NotValidParameterException, IllegalTimeException;
}
