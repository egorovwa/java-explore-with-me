package ru.practicum.ewmmainservice.privateservise.event;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.event.dto.UpdateEventRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class PrivateEventController {
    private final PrivateEventService eventService;

    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(@Positive @PathVariable("userId") Long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) throws FiledParamNotFoundException {
        return eventService.createEvent(userId, newEventDto);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto patchEvent(@Positive @PathVariable("userId") Long userId,
                                   @Valid @RequestBody UpdateEventRequest requestEvent) throws NotFoundException, StatusException, IllegalTimeException, IlegalUserIdException {
        return eventService.patchEvent(userId, requestEvent);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto eventСancellation(@Positive @PathVariable("userId") Long userId,
                                          @Positive @PathVariable("eventId") Long eventId) throws NotFoundException, StatusException {
        return eventService.eventСancellation(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findEventForInitiator(@Positive @PathVariable("userId") Long userId,
                                              @Positive @PathVariable("eventId") Long eventId) throws NotFoundException, IlegalUserIdException {
        return eventService.findEventForInitiator(userId, eventId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> findAllEventByInitiator(@Positive @PathVariable("userId") Long userId) throws NotFoundException {
        return eventService.findAllEventByInitiator(userId);
    }
}
