package ru.practicum.ewmmainservice.adminService.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersAdminFindEvent;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventControlller {
    private final AdminEventService service;

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable("eventId") Long eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) throws NotFoundException {
        return service.updateEventRequest(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@Positive @PathVariable Long eventId) throws NotFoundException, StatusException, IllegalTimeException {
        return service.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@Positive @PathVariable Long eventId) throws NotFoundException, StatusException, IllegalTimeException {
        return service.rejectEvent(eventId);
    }

    @GetMapping
    public List<EventFullDto> findAllEvents(@RequestParam(value = "users",required = false) Long[] users,
                                            @RequestParam(value = "states",required = false) String[] states,
                                            @RequestParam(value = "categories", required = false) Long[] categories,
                                            @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                            @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
                                            @Positive @RequestParam(value = "size", defaultValue = "10") int size)
            throws IncorrectPageValueException, NotValidParameterException, IllegalTimeException {

        ParametersAdminFindEvent parameters = new ParametersAdminFindEvent(users, states, categories,
                rangeStart, rangeEnd, from, size);

        return service.findAllEvents(parameters);
    }


}
