package ru.practicum.ewmmainservice.privateservise.event;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class PrivateEventController {
    private final PrivateEventService eventService;
    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(@Positive @PathParam("userId") Long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) throws FiledParamNotFoundException {
        return eventService.createEvent(userId, newEventDto);
    }
}
