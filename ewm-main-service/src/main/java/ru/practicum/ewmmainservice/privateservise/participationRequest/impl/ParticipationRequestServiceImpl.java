package ru.practicum.ewmmainservice.privateservise.participationRequest.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.event.AdminEventService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.participationRequest.RequestStatus;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.participationRequest.ParticipationRequestRepository;
import ru.practicum.ewmmainservice.privateservise.participationRequest.ParticipationRequestService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository repository;
    private final UserAdminService userService;
    private final AdminEventService eventService;
    private final ParticipationRequestDtoMaper dtoMaper;

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) throws NotFoundException,
            FiledParamNotFoundException, IlegalUserIdException, EventStatusException, NumberParticipantsExceededException {
        try {
            User user = userService.findById(userId);
            Event event = eventService.findById(eventId);
            if (!user.equals(event.getInitiator())) {
                if (event.getState().equals(EventState.PUBLISHED)) {
                    if (event.getParticipantLimit() < event.getParticipants().size()) {
                        if (!event.getRequestModeration()) {
                            event.getParticipants().add(user);
                            eventService.save(event);
                            ParticipationRequest request = new ParticipationRequest();
                            request.setRequester(user);
                            request.setEvent(event);
                            request.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
                            request.setStatus(RequestStatus.ACCEPTED);
                            return dtoMaper.toDto(repository.save(request));
                        } else {
                            return dtoMaper.toDto(repository.save(new ParticipationRequest(LocalDateTime.now()
                                    .toEpochSecond(ZoneOffset.UTC), event, null, user, RequestStatus.PENDING)));
                        }
                    } else {
                        throw new NumberParticipantsExceededException(String.format("The number of participants exceeded max = %s",
                                event.getParticipantLimit()), event.getParticipantLimit());
                    }

                } else {
                    throw new EventStatusException("You cannot participate in an unpublished event");
                }

            } else {
                throw new IlegalUserIdException("The initiator of the event cannot request participation",
                        userId.toString(), "Event");
            }
        } catch (NotFoundException e) {
            throw new FiledParamNotFoundException(String.format("%s %s %s  not found", e.getClassName(),
                    e.getValue(), e.getParam()));
        }
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public ParticipationRequestDto findRequests(Long userId) {
        return null;
    }
}
