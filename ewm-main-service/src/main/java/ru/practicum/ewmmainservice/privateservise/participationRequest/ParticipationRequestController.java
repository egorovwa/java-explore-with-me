package ru.practicum.ewmmainservice.privateservise.participationRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.nio.file.Path;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ParticipationRequestController {
    private final ParticipationRequestService service;

    @PostMapping("/{userId}/requests")
    @Validated
    public ParticipationRequestDto createRequest(@Positive @PathVariable("userId") Long userId,
                                                 @Positive @PathParam("eventId") Long eventId) {
        return service.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @Validated
    public ParticipationRequestDto cancelRequest(@Positive @PathVariable("userId") Long userId,
                                                 @Positive @PathVariable("requestId") Long requestId) {
        return service.cancelRequest(userId, requestId);
    }
    @GetMapping("/{userId}/requests")
    public ParticipationRequestDto findRequests(@Positive @PathVariable("userId") Long userId){
        return service.findRequests(userId);
    }
}