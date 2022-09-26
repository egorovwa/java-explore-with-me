package ru.practicum.ewmmainservice.adminService.event;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.NotValidParameterException;
import ru.practicum.ewmmainservice.exceptions.StatusException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersAdminFindEvent;

import java.util.Collection;
import java.util.List;

public interface AdminEventService {
    Collection<Event> findByCategoryId(Long catId);

    EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) throws NotFoundException;

    EventFullDto publishEvent(Long eventId) throws NotFoundException, IllegalTimeException, StatusException;

    EventFullDto rejectEvent(Long eventId) throws NotFoundException, StatusException, IllegalTimeException;
    Event findById(Long eventId) throws NotFoundException;

    void save(Event event);

    List<EventFullDto> findAllEvents(ParametersAdminFindEvent parameters) throws NotValidParameterException;
}
