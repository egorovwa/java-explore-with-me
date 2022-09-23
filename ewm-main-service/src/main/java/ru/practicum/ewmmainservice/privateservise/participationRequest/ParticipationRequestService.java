package ru.practicum.ewmmainservice.privateservise.participationRequest;

import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDto;

public interface ParticipationRequestService {
    ParticipationRequestDto createRequest(Long userId, Long eventId) throws NotFoundException, FiledParamNotFoundException, IlegalUserIdException, EventStatusException, NumberParticipantsExceededException;

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    ParticipationRequestDto findRequests(Long userId);
}
