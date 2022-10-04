package ru.practicum.ewmmainservice.adminService.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.event.AdminEventService;
import ru.practicum.ewmmainservice.adminService.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.parameters.ParametersAdminFindEvent;
import ru.practicum.ewmmainservice.models.parameters.ParametersValidator;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmstatscontract.utils.Utils.HOUR;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {
    private final AdminEwentRepository repository;
    private final CategoryService categoryService;
    private final DateTimeFormatter formatter = Utils.getDateTimeFormatter();
    private final LocationDtoMaper locationDtoMaper;
    private final LocationService locationService;
    private final EventDtoMaper eventDtoMaper;
    private final ParametersValidator validator;
    private final CategoryDtoMaper categoryDtoMaper;

    @Override
    public Collection<Event> findByCategoryId(Long catId) {
        log.info("Find event by category id id {}", catId);
        return repository.findAllByCategoryId(catId);
    }

    @Override
    public EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest request) throws NotFoundException, FiledParamNotFoundException {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("id", eventId.toString(), "Event"));
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
            log.info("Updated the annotation of the event id = {}", eventId);
        }
        try {
            if (request.getCategory() != null && request.getCategory() != 0) {
                Category category = categoryService.findByid(request.getCategory());
                event.setCategory(category);
                log.info("Updated the category of the event id = {}", eventId);
            }
        } catch (NotFoundException e) {
            throw new FiledParamNotFoundException(String.format("%s %s = %s not found", e.getClassName(),
                    e.getValue(), e.getParam()));
        }

        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
            log.info("Updated the description of the event id = {}", eventId);
        }
        if (request.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(request.getEventDate(), formatter).toEpochSecond(ZoneOffset.UTC));
            log.info("Updated the eventDate of the event id = {}", eventId);
        }
        if (request.getLocation() != null) {
            Location location = locationService.save(request.getLocation());
            event.setLocation(location);
            log.info("Updated the location of the event id = {}", eventId);
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
            log.info("Updated the paid of the event id = {}", eventId);
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
            log.info("Updated the ParticipantLimit of the event id = {}", eventId);
        }
        if (request.getRequestModeration() != null) {
            event.setRequestModeration(request.getRequestModeration());
            log.info("Updated the RequestModeration of the event id = {}", eventId);

        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
            log.info("Updated the Title of the event id = {}", eventId);
        }
        return eventDtoMaper.toFulDto(repository.save(event));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) throws NotFoundException, IllegalTimeException, StatusException {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("id", eventId.toString(), "Event"));
        if (event.getEventDate() > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + HOUR) {
            if (event.getState().equals(EventState.PENDING)) {
                log.info("Event published  {}", formatter.format(LocalDateTime.now()));
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
                return eventDtoMaper.toFulDto(repository.save(event));
            } else {
                log.warn("Publication of the event  by the status not WAITING");
                throw new StatusException("Status event is not WAITING");
            }
        } else {
            log.warn("Publication of the event starts in less than an hour");
            throw new IllegalTimeException("The event starts in less than an hour.",
                    formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)));
        }
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) throws NotFoundException, StatusException, IllegalTimeException {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("id", eventId.toString(), "Event"));
        if (event.getEventDate() > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + HOUR) {
            if (event.getState().equals(EventState.PENDING)) {
                event.setState(EventState.CANCELED);
                log.info("Event reject  {}", formatter.format(LocalDateTime.now()));
                return eventDtoMaper.toFulDto(repository.save(event));
            } else {
                log.warn("Reject of the event  by the status not WAITING");
                throw new StatusException("Status event is not WAITING");
            }
        } else {
            log.warn("Reject of the event starts in less than an hour");
            throw new IllegalTimeException("The event starts in less than an hour.",
                    formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)));
        }
    }

    @Override
    public Event findById(Long eventId) throws NotFoundException {
        return repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("id", eventId.toString(), "Event"));
    }

    @Override
    public void save(Event event) {
        log.info("Event {} saved.", event);
        repository.save(event);
    }

    @Override
    public List<EventFullDto> findAllEvents(ParametersAdminFindEvent parameters) throws NotValidParameterException, IllegalTimeException {
        validator.adminFindEvents(parameters);
        List<Event> events = repository.findForAdmin(parameters.getUsers(), parameters.getCategories(),
                        parameters.getStates(), parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageable())
                .toList();
        log.info("Find events with parameters {}", parameters);
        return events.stream().map(eventDtoMaper::toFulDto).collect(Collectors.toList());
    }

}