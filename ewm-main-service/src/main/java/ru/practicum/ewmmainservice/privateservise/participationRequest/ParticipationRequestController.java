package ru.practicum.ewmmainservice.privateservise.participationRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ParticipationRequestController {
    private final ParticipationRequestService service;

    @PostMapping("/{userId}/requests")
    @Validated
    public ParticipationRequestDto createRequest(@Positive @PathVariable("userId") Long userId,
                                                 @Positive @PathParam("eventId") Long eventId)
            throws NumberParticipantsExceededException, NotFoundException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
        return service.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @Validated
    public ParticipationRequestDto cancelRequest(@Positive @PathVariable("userId") Long userId,
                                                 @Positive @PathVariable("requestId") Long requestId) throws NotFoundException, IlegalUserIdException {
        return service.cancelRequest(userId, requestId);
    }
    @GetMapping("/{userId}/requests")
    public Collection<ParticipationRequestDto> findRequests(@Positive @PathVariable("userId") Long userId){
        return service.findRequests(userId);
    }
    @PatchMapping("{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@Positive @PathVariable("userId") Long userId,
                                                       @Positive @PathVariable("eventId") Long eventId,
                                                       @Positive @PathVariable("reqId") Long reqId) throws NotRequiredException, NumberParticipantsExceededException, FiledParamNotFoundException, NotFoundException, IlegalUserIdException {
        return service.confirmRequest(userId, eventId, reqId);
    }
    @PatchMapping("{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@Positive @PathVariable("userId") Long userId,
                                                 @Positive @PathVariable("eventId") Long eventId,
                                                 @Positive @PathVariable("reqId") Long reqId) throws NotFoundException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
    return service.rejectRequest(userId, eventId, reqId);
    }
    @GetMapping("{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> finndRequestEventByUser(@Positive @PathVariable("userId") Long userId,
                                                                 @Positive @PathVariable("eventId") Long eventId) throws NotFoundException, IlegalUserIdException {
        return service.finndRequestEventByUser(userId, eventId);
    }
}
