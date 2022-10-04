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
import ru.practicum.ewmmainservice.models.event.dto.*;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventService;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.ewmstatscontract.utils.Utils.HOUR;
import static ru.practicum.ewmstatscontract.utils.Utils.getDateTimeFormatter;


@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {
    private final CategoryService categoryService;
    private final UserAdminService userAdminService;
    private final PrivateEventRepository repository;
    private final EventDtoMaper eventDtoMaper;
    private final LocationService locationService;
    private final DateTimeFormatter formatter = getDateTimeFormatter();

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) throws FiledParamNotFoundException {

        try {
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
    public EventFullDto patchEvent(Long userId, UpdateEventRequest requestEvent) throws StatusException, IllegalTimeException, NotFoundException, IlegalUserIdException {
        Event event = repository.findById(requestEvent.getEventId())
                .orElseThrow(() -> new NotFoundException("id", String.valueOf(requestEvent.getEventId()), "Event"));
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            if (event.getState().equals(EventState.PENDING) || event.getState().equals(EventState.CANCELED)) {
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
                throw new StatusException(String.format("Only pending or canceled events can be changed", event.getId()));
            }

        } else {
            throw new IlegalUserIdException(userId, event.getId(), "Event");
        }
    }

    @Override
    public EventFullDto eventСancellation(Long userId, Long eventId) throws NotFoundException, StatusException {
        User user = userAdminService.findById(userId);
        Event event = repository.findById(eventId).orElseThrow(() ->
                new NotFoundException("id", eventId.toString(), "Event"));
        if (event.getState().equals(EventState.PENDING)) {
            event.setState(EventState.CANCELED);
            return eventDtoMaper.toFulDto(repository.save(event));
        } else {
            throw new StatusException("Only pending  events can be changed");
        }
    }

    @Override
    public EventFullDto findEventForInitiator(Long userId, Long eventId) throws NotFoundException, IlegalUserIdException {
        User user = userAdminService.findById(userId);
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("id", eventId.toString(), "Event"));
        if (user.equals(event.getInitiator())) {
            return eventDtoMaper.toFulDto(event);
        } else {
            throw new IlegalUserIdException(userId, eventId, "Event");
        }

    }

    @Override
    public List<EventShortDto> findAllEventByInitiator(Long userId) throws NotFoundException {
        userAdminService.findById(userId);
        return repository.findByInitiatorId(userId).stream().map(eventDtoMaper::toShortDto).collect(Collectors.toList());
    }
}
