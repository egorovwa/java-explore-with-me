package ru.practicum.ewmmainservice.privateservise.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.event.dto.UpdateEventRequest;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventService;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static ru.practicum.ewmmainservice.utils.Utils.HOUR;
import static ru.practicum.ewmmainservice.utils.Utils.getDateTimeFormater;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {
    private final CategoryService categoryService;
    private final UserAdminService userAdminService;
    private final PrivateEventRepository repository;
    private final EventDtoMaper eventDtoMaper;
    private final LocationService locationService;
    private final DateTimeFormatter formatter = getDateTimeFormater();

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) throws FiledParamNotFoundException {

        try { // TODO: 21.09.2022 duplicate?
            Category category = categoryService.findByid(newEventDto.getCategory());
            User user = userAdminService.findById(userId);
            Location location = locationService
                    .findByLatAndLon(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon())
                    .orElseGet(() -> locationService.save(newEventDto.getLocation()));
            log.info("Created new event {}", newEventDto);
            return eventDtoMaper.toFulDto(repository.save(eventDtoMaper.fromNewDto(newEventDto, category, user, location)));
        } catch (NotFoundException e) {
            log.warn("{} with id {} not found", e.getClassName(), e.getValue());
            throw new FiledParamNotFoundException(String.format("%s %s %s not found", e.getClassName(), e.getParam(),
                    e.getValue()));
        }
    }

    @Override
    public EventFullDto patchEvent(Long userId, UpdateEventRequest requestEvent) throws EventStatusException, IllegalTimeException, NotFoundException, IlegalUserIdException {
        Event event = repository.findById(requestEvent.getEventId())
                .orElseThrow(() -> new NotFoundException(String.format("Event with id= %d was not found.",
                        requestEvent.getEventId()), "id", String.valueOf(requestEvent.getEventId()), "Event"));
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            if (event.getState().equals(EventState.WAITING) || event.getState().equals(EventState.CANCELLED)) {
                if (event.getEventDate() > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + HOUR * 2) {
                    if (requestEvent.getAnnotation() != null) {
                        event.setAnnotation(requestEvent.getAnnotation());
                        log.info("The user id = {} updated the event id = {} Annotation", userId, event.getId());
                    }
                    if (requestEvent.getCategory() != null) {
                        event.setCategory(categoryService.findByid(requestEvent.getCategory()));
                        log.info("The user id = {} updated the event id = {} category", userId, event.getId());
                    }
                    if (requestEvent.getDescription() != null) {
                        event.setDescription(requestEvent.getDescription());
                        log.info("The user id = {} updated the event id = {} Description", userId, event.getId());
                    }
                    if (requestEvent.getEventDate() != null) {
                        Long reuestDate = LocalDateTime.parse(requestEvent.getEventDate(), formatter)
                                .toEpochSecond(ZoneOffset.UTC);
                        if (reuestDate > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + HOUR * 2) {
                            event.setEventDate(reuestDate);
                            log.info("The user id = {} updated the event id = {} EventDate", userId, event.getId());
                        } else {
                            throw new IllegalTimeException("The event should start no earlier than 2 hours later",
                                    formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)));
                        }
                    }
                    if (requestEvent.getPaid() != null) {
                        event.setPaid(requestEvent.getPaid());
                        log.info("The user id = {} updated the event id = {} Paid", userId, event.getId());
                    }
                    if (requestEvent.getParticipantLimit() != null) {
                        event.setParticipantLimit(requestEvent.getParticipantLimit());
                        log.info("The user id = {} updated the event id = {} ParticipantLimit", userId, event.getId());
                    }
                    if (requestEvent.getTitle() != null) {
                        event.setTitle(requestEvent.getTitle());
                        log.info("The user id = {} updated the event id = {} Title", userId, event.getId());
                    }
                    return eventDtoMaper.toFulDto(repository.save(event));
                } else {
                    throw new IllegalTimeException("The event should start no earlier than 2 hours later",
                            formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)));
                }

            } else {
                throw new EventStatusException(String.format("Only pending or canceled events can be changed", event.getId()));
            }

        } else {
            throw new IlegalUserIdException(String.format("The user id= %s is not the initiator of the event id = %s",
                    userId, event.getId()), "id", event.getId().toString());
        }
    }

    @Override
    public EventFullDto eventСancellation(Long userId, Long eventId) throws NotFoundException, EventStatusException {
        User user = userAdminService.findById(userId);
        Event event = repository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id= %s was not found.", eventId), "id", eventId.toString(), "Event"));
        if (event.getState().equals(EventState.WAITING)) {
            event.setState(EventState.CANCELLED);
            return eventDtoMaper.toFulDto(repository.save(event));
        } else {
            throw new EventStatusException("Only pending  events can be changed");
        }
    }
}
