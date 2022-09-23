package ru.practicum.ewmmainservice.adminService.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.event.AdminEventService;
import ru.practicum.ewmmainservice.adminService.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.exceptions.EventStatusException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMaper;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;
import ru.practicum.ewmmainservice.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static ru.practicum.ewmmainservice.utils.Utils.HOUR;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {
    private final AdminEwentRepository repository;
    private final CategoryService categoryService;
    private final DateTimeFormatter formatter = Utils.getDateTimeFormater();
    private final LocationDtoMaper locationDtoMaper;
    private final LocationService locationService;
    private final EventDtoMaper eventDtoMaper = new EventDtoMaper(new UserDtoMaper(),
            new LocationDtoMaper());

    @Override
    public Collection<Event> findByCategoryId(Long catId) {
        log.info("Find event by categoty id id {}", catId);
        return repository.findAllByCategoryId(catId);
    }

    @Override
    public EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest request) throws NotFoundException {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not exist", "id", eventId.toString(), "Event"));
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
            log.info("Updated the annotation of the event id = {}", eventId);
        }
        if (request.getCategory() != null && request.getCategory() != 0) {
            categoryService.findByid(request.getCategory());
            event.setAnnotation(request.getAnnotation());
            log.info("Updated the category of the event id = {}", eventId);
        }
        if (request.getDescription() != null) {
            event.setAnnotation(request.getDescription());
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
    public EventFullDto publishEvent(Long eventId) throws NotFoundException, IllegalTimeException, EventStatusException {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not exist", "id", eventId.toString(), "Event"));
        if (event.getEventDate() > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + HOUR) {
            if (event.getState().equals(EventState.WAITING)) {
                log.info("Event published  {}",formatter.format(LocalDateTime.now()));
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
                return eventDtoMaper.toFulDto(repository.save(event));
            } else {
                log.warn("Publication of the event  by the status not WAITING");
                throw new EventStatusException("Status event is not WAITING");
            }
        } else {
            log.warn("Publication of the event starts in less than an hour");
            throw new IllegalTimeException("The event starts in less than an hour.",
                    formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)));
        }
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) throws NotFoundException, EventStatusException, IllegalTimeException {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not exist", "id", eventId.toString(), "Event"));
        if (event.getEventDate() > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + HOUR) {
            if (event.getState().equals(EventState.WAITING)) {
                event.setState(EventState.CANCELLED);
                log.info("Event reject  {}",formatter.format(LocalDateTime.now()));
                return eventDtoMaper.toFulDto(repository.save(event));
            } else {
                log.warn("Reject of the event  by the status not WAITING");
                throw new EventStatusException("Status event is not WAITING");
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
                .orElseThrow(()-> new  NotFoundException(String.format("Event id= %s not found", eventId),
                        "id", eventId.toString(),"Event"));
    }

    @Override
    public void save(Event event) {
        repository.save(event);
    }
}