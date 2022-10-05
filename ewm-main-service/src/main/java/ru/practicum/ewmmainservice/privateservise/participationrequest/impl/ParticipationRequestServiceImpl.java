package ru.practicum.ewmmainservice.privateservise.participationrequest.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminservice.event.AdminEventService;
import ru.practicum.ewmmainservice.adminservice.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.participationRequest.RequestStatus;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.participationrequest.ParticipationRequestRepository;
import ru.practicum.ewmmainservice.privateservise.participationrequest.ParticipationRequestService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository repository;
    private final UserAdminService userService;
    private final AdminEventService eventService;
    private final ParticipationRequestDtoMaper dtoMaper;

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) throws
            FiledParamNotFoundException, IlegalUserIdException, StatusException, NumberParticipantsExceededException {
        try {
            User user = userService.findById(userId);
            Event event = eventService.findById(eventId);
            if (user.equals(event.getInitiator())) {
                log.warn("Participation Request from initiator requester id = {}, event id={}", userId, eventId);
                throw new IlegalUserIdException(String
                        .format("The initiator id = %s of the event id = %s cannot request participation", userId, eventId),
                        userId, eventId, "Event");
            }
            if (!event.getState().equals(EventState.PUBLISHED)) {
                log.warn("Participation Request with unpublished event id= {}", eventId);
                throw new StatusException("You cannot participate in an unpublished event");
            }
            if (!event.getRequestModeration()) {
                if (event.getParticipantLimit() <= event.getParticipants().size()) {
                    log.warn("The number of participants exceeded max = {}", event.getParticipantLimit());
                    throw new NumberParticipantsExceededException(event.getParticipantLimit());
                }
                event.getParticipants().add(user);
                eventService.save(event);
                ParticipationRequest request = new ParticipationRequest();
                request.setRequester(user);
                request.setEvent(event);
                request.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
                request.setStatus(RequestStatus.CONFIRMED);
                log.info("Created Participation Request {}", request);
                return dtoMaper.toDto(repository.save(request));
            } else {
                ParticipationRequest request = new ParticipationRequest(LocalDateTime.now()
                        .toEpochSecond(ZoneOffset.UTC), event, null, user, RequestStatus.PENDING);
                log.info("Created Participation Request {}", request);
                return dtoMaper.toDto(repository.save(request));
            }
        } catch (NotFoundException e) {
            log.warn("{} {} {} notFoud", e.getClassName(), e.getValue(), e.getParam());
            throw new FiledParamNotFoundException(String.format("%s %s %s  not found", e.getClassName(),
                    e.getValue(), e.getParam()));
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) throws NotFoundException, IlegalUserIdException {
        User user = userService.findById(userId);
        ParticipationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("id", requestId.toString(), "ParticipationRequest"));
        if (user.equals(request.getRequester())) {
            request.setStatus(RequestStatus.CANCELED);
            log.info("Participation Request id {} canceled by requester id {}", requestId, userId);
            return dtoMaper.toDto(repository.save(request));
        } else {
            log.warn("The user id {} is not the owner of the request id {}", userId, requestId);
            throw new IlegalUserIdException(String.format("The user id %s is not the owner of the request id %s", userId,
                    requestId), userId, requestId, "ParticipationRequest");
        }

    }

    @Override
    public Collection<ParticipationRequestDto> findRequests(Long userId) {
        log.info("Find ParticipationRequest for user id = {}", userId);
        return repository.findAllByRequesterId(userId).stream().map(dtoMaper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) throws FiledParamNotFoundException, NotRequiredException, NumberParticipantsExceededException, NotFoundException, IlegalUserIdException {
        ParticipationRequest request = repository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("id", reqId.toString(), "ParticipationRequest"));
        try {
            User user = userService.findById(userId);
            Event event = eventService.findById(eventId);
            if (!user.equals(event.getInitiator())) {
                throw new IlegalUserIdException(String.format("The user id = %s is not the creator of the event id = %s.",
                        userId, eventId), userId, eventId, "event");
            }
            if (!event.getRequestModeration()) {
                throw new NotRequiredException("Confirmation of the request is not required.");
            }
            if (event.getParticipantLimit() <= event.getParticipants().size()) {
                throw new NumberParticipantsExceededException(event.getParticipantLimit());
            }
            event.getParticipants().add(user);
            eventService.save(event);
            if (event.getParticipants().size() == event.getParticipantLimit()) {
                Collection<ParticipationRequest> requests =
                        repository.findAllByEventIdAndStatus(eventId, RequestStatus.PENDING);
                requests.stream().peek(r -> r.setStatus(RequestStatus.REJECTED))
                        .forEach(repository::save);
            }
            request.setStatus(RequestStatus.CONFIRMED);
            log.info("Confirmed Request {}", request);
            return dtoMaper.toDto(repository.save(request));
        } catch (NotFoundException e) {
            throw new FiledParamNotFoundException(String.format("%s %s %s  not found", e.getClassName(),
                    e.getValue(), e.getParam()));
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) throws NotFoundException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
        ParticipationRequest request = repository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("id", reqId.toString(), "ParticipationRequest"));
        try {
            User user = userService.findById(userId);
            Event event = eventService.findById(eventId);
            if (!user.equals(event.getInitiator())) {
                throw new IlegalUserIdException(String.format("The user id = %s is not the creator of the event id = %s.",
                        userId, eventId), userId, eventId, "event");
            }
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new StatusException("Status must be PENDING");
            }
            request.setStatus(RequestStatus.REJECTED);
            log.info("Rejected Request {}", request);
            return dtoMaper.toDto(repository.save(request));
        } catch (NotFoundException e) {
            throw new FiledParamNotFoundException(String.format("%s %s %s  not found", e.getClassName(),
                    e.getValue(), e.getParam()));
        }
    }

    @Override
    public List<ParticipationRequestDto> finndRequestEventByUser(Long userId, Long eventId) throws NotFoundException, IlegalUserIdException {
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);
        if (user.equals(event.getInitiator())) {
            log.info("Find request for user id = {}", userId);
            return repository.findAllByEventId(eventId).stream().map(dtoMaper::toDto).collect(Collectors.toList());
        } else {
            throw new IlegalUserIdException(userId, eventId, "Event");
        }
    }
}
