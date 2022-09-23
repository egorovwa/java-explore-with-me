package ru.practicum.ewmmainservice.adminService.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.EventStatusException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/events/")
@RequiredArgsConstructor
public class AdminEventControlller {
    private final AdminEventService service;
    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable("eventId") Long eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) throws NotFoundException {
        return service.updateEventRequest(eventId, adminUpdateEventRequest);
    }
    @PatchMapping("{eventId}/publish")
    public EventFullDto publishEvent(@Positive @PathVariable Long eventId) throws NotFoundException, EventStatusException, IllegalTimeException {
        return service.publishEvent(eventId);
    }
    @PatchMapping("{eventId}/reject")
    public EventFullDto rejectEvent(@Positive @PathVariable Long eventId) throws NotFoundException, EventStatusException, IllegalTimeException {
        return service.rejectEvent(eventId);
    }
}
