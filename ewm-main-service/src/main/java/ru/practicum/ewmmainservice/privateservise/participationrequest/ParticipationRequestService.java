package ru.practicum.ewmmainservice.privateservise.participationrequest;


import com.example.evmdtocontract.dto.participationrequest.ParticipationRequestDto;
import ru.practicum.ewmmainservice.exceptions.*;

import java.util.Collection;
import java.util.List;

public interface ParticipationRequestService {
    ParticipationRequestDto createRequest(Long userId, Long eventId) throws FiledParamNotFoundException, IlegalUserIdException, StatusException, NumberParticipantsExceededException;

    ParticipationRequestDto cancelRequest(Long userId, Long requestId) throws NotFoundException, IlegalUserIdException;

    Collection<ParticipationRequestDto> findRequests(Long userId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) throws FiledParamNotFoundException, NotRequiredException, NumberParticipantsExceededException, NotFoundException, IlegalUserIdException;

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) throws NotFoundException, FiledParamNotFoundException, StatusException, IlegalUserIdException;

    List<ParticipationRequestDto> finndRequestEventByUser(Long userId, Long eventId) throws NotFoundException, IlegalUserIdException;
}
