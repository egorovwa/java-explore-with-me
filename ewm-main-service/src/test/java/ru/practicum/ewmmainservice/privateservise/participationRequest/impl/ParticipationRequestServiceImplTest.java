package ru.practicum.ewmmainservice.privateservise.participationRequest.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewmmainservice.adminService.event.AdminEventService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.participationRequest.RequestStatus;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.participationRequest.ParticipationRequestRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipationRequestServiceImplTest {
    @Mock
    private ParticipationRequestRepository repository;
    @Mock
    private UserAdminService userService;
    @Mock
    private AdminEventService eventService;
    @Mock
    private final ParticipationRequestDtoMaper dtoMaper = new ParticipationRequestDtoMaper();
    @InjectMocks
    private ParticipationRequestServiceImpl service;

    @Test
    void test1_1createRequest() throws NotFoundException, NumberParticipantsExceededException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, null, user, RequestStatus.PENDING);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        ParticipationRequestDto requestDto = service.createRequest(1L, 1L);
        verify(repository, times(1)).save(request);
    }

    @Test
    void test1_2createRequest_whenStateNotPublished() throws NotFoundException, NumberParticipantsExceededException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, null, user, RequestStatus.PENDING);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        assertThrows(StatusException.class, () -> {
            ParticipationRequestDto requestDto = service.createRequest(1L, 1L);
        });
    }

    @Test
    void test1_3createRequest_whenUserInitiator() throws NotFoundException, NumberParticipantsExceededException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, null, user, RequestStatus.PENDING);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        assertThrows(IlegalUserIdException.class, () -> {
            ParticipationRequestDto requestDto = service.createRequest(1L, 1L);
        });
    }

    @Test
    void test1_4createRequest_wheParticipantLimit() throws NotFoundException, NumberParticipantsExceededException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(5L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 1, null, false, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, null, user, RequestStatus.PENDING);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        assertThrows(NumberParticipantsExceededException.class, () -> {
            ParticipationRequestDto requestDto = service.createRequest(1L, 1L);
        });
    }

    @Test
    void test2_1cancelRequest() throws NotFoundException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(5L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 1, null, true, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.PENDING);
        ParticipationRequest requestSaved = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.CANCELED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        ParticipationRequestDto requestDto = service.cancelRequest(1L, 1L);
        verify(repository, times(1)).save(requestSaved);

    }

    @Test
    void test2_2cancelRequest_wheRequesterNotUser() throws NotFoundException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(5L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 1, null, true, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, 1L, new User(3L, "ddd@mail.com", "nn"), RequestStatus.PENDING);
        ParticipationRequest requestSaved = new ParticipationRequest(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.REJECTED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        assertThrows(IlegalUserIdException.class, () -> {
            ParticipationRequestDto requestDto = service.cancelRequest(1L, 1L);
        });
    }

    @Test
    void test2_3cancelRequest_wheRequestNotFound() throws NotFoundException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");

        when(userService.findById(1L))
                .thenReturn(user);
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            ParticipationRequestDto requestDto = service.cancelRequest(1L, 1L);
        });
    }

    @Test
    void test3_findRequests() {
        service.findRequests(1L);
        verify(repository, times(1)).findAllByRequesterId(1L);
    }

    @Test
    void test3_confirmRequest() throws NotFoundException, NotRequiredException, NumberParticipantsExceededException, FiledParamNotFoundException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        User user2 = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        List<User> participantsSaved = new ArrayList<>();
        participantsSaved.add(new User(2L, "email2@mail.ru", "name2"));
        participantsSaved.add(user);
        participants.add(new User(2L, "email2@mail.ru", "name2"));
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PUBLISHED, "title", 2,
                participants);
        Event eventSaved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PUBLISHED, "title", 2,
                participantsSaved);
        List<ParticipationRequest> participationRequests = List.of(new ParticipationRequest(LocalDateTime.now().
                minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 2L, user2, RequestStatus.PENDING));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.PENDING);
        ParticipationRequest requestSave = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.CONFIRMED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
/*        when(repository.findAllByEventIdAndStatus(1l, RequestStatus.PENDING))
                .thenReturn(participationRequests);*/
        ParticipationRequestDto result = service.confirmRequest(1L, 1L, 1L);
        verify(repository, times(1)).save(requestSave);
        verify(eventService, times(1)).save(eventSaved);
    }

    @Test
    void test3_2confirmRequest_whenLimitAfte() throws NotFoundException, NotRequiredException, NumberParticipantsExceededException, FiledParamNotFoundException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        User user2 = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        List<User> participantsSaved = new ArrayList<>();
        participantsSaved.add(new User(2L, "email2@mail.ru", "name2"));
        participantsSaved.add(user);
        participants.add(new User(2L, "email2@mail.ru", "name2"));
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 2, null, true, EventState.PUBLISHED, "title", 2,
                participants);
        Event eventSaved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 2, null, true, EventState.PUBLISHED, "title", 2,
                participantsSaved);
        List<ParticipationRequest> participationRequests = List.of(new ParticipationRequest(LocalDateTime.now().
                minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 2L, user2, RequestStatus.PENDING));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.PENDING);
        ParticipationRequest requestSave = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.CONFIRMED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        when(repository.findAllByEventIdAndStatus(1l, RequestStatus.PENDING))
                .thenReturn(participationRequests);
        ParticipationRequestDto result = service.confirmRequest(1L, 1L, 1L);
        verify(repository, times(1)).save(requestSave);
        verify(repository, times(1)).save(new ParticipationRequest(LocalDateTime.now().
                minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 2L, user2, RequestStatus.REJECTED));
        verify(eventService, times(1)).save(eventSaved);

    }

    @Test
    void test3_3confirmRequest_whenModerationFalse() throws NotFoundException, NotRequiredException, NumberParticipantsExceededException, FiledParamNotFoundException {
        User user = new User(1L, "enail@mail.ru", "name");
        User user2 = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        List<User> participantsSaved = new ArrayList<>();
        participantsSaved.add(new User(2L, "email2@mail.ru", "name2"));
        participantsSaved.add(user);
        participants.add(new User(2L, "email2@mail.ru", "name2"));
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 2, null, false, EventState.PUBLISHED, "title", 2,
                participants);
        List<ParticipationRequest> participationRequests = List.of(new ParticipationRequest(LocalDateTime.now().
                minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 2L, user2, RequestStatus.PENDING));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.PENDING);
        ParticipationRequest requestSave = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.CONFIRMED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        assertThrows(NotRequiredException.class, () -> {
            ParticipationRequestDto result = service.confirmRequest(1L, 1L, 1L);
        });
    }

    @Test
    void test3_3confirmRequest_whenLimit() throws NotFoundException, NotRequiredException, NumberParticipantsExceededException, FiledParamNotFoundException {
        User user = new User(1L, "enail@mail.ru", "name");
        User user2 = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        List<User> participantsSaved = new ArrayList<>();
        participantsSaved.add(new User(2L, "email2@mail.ru", "name2"));
        participantsSaved.add(user);
        participants.add(new User(2L, "email2@mail.ru", "name2"));
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 1, null, true, EventState.PUBLISHED, "title", 2,
                participants);
        Event eventSaved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 2, null, true, EventState.PUBLISHED, "title", 2,
                participantsSaved);
        List<ParticipationRequest> participationRequests = List.of(new ParticipationRequest(LocalDateTime.now().
                minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 2L, user2, RequestStatus.PENDING));
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.PENDING);
        ParticipationRequest requestSave = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, user, RequestStatus.CONFIRMED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        assertThrows(NumberParticipantsExceededException.class, () -> {
            ParticipationRequestDto result = service.confirmRequest(1L, 1L, 1L);

        });
    }

    @Test
    void test3_2confirmRequest_whenRequestNotFound() throws NotFoundException, NotRequiredException, NumberParticipantsExceededException, FiledParamNotFoundException {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            ParticipationRequestDto result = service.confirmRequest(1L, 1L, 1L);
        });
    }

    @Test
    void test4_1rejectRequest() throws NotFoundException, FiledParamNotFoundException, StatusException, IlegalUserIdException {
        User user = new User(1L, "enail@mail.ru", "name");
        User requestor = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 1, null, true, EventState.PUBLISHED, "title", 2,
                participants);
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.PENDING);
        ParticipationRequest requestSaved = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.REJECTED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        ParticipationRequestDto result = service.rejectRequest(1L, 1L, 1L);
        verify(repository, times(1)).save(requestSaved);

    }

    @Test
    void test4_2rejectRequest_whenRequestNotFound() throws NotFoundException, FiledParamNotFoundException, StatusException {
        User user = new User(1L, "enail@mail.ru", "name");
        User requestor = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 1, null, true, EventState.PUBLISHED, "title", 2,
                participants);
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.PENDING);
        ParticipationRequest requestSaved = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.REJECTED);
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            ParticipationRequestDto result = service.rejectRequest(1L, 1L, 1L);

        });
    }

    @Test
    void test4_3rejectRequest_wheUserNotInitiator() throws NotFoundException, FiledParamNotFoundException, StatusException {
        User user = new User(1L, "enail@mail.ru", "name");
        User requestor = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                requestor, new Location(1L, 1.0f, 2.0f),
                false, 1, null, true, EventState.PUBLISHED, "title", 2,
                participants);
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.PENDING);
        ParticipationRequest requestSaved = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.REJECTED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        assertThrows(IlegalUserIdException.class, () -> {
            ParticipationRequestDto result = service.rejectRequest(1L, 1L, 1L);
        });
    }
    @Test
    void test4_4rejectRequest_whenStatusNotPending() throws NotFoundException, FiledParamNotFoundException, StatusException {
        User user = new User(1L, "enail@mail.ru", "name");
        User requestor = new User(2L, "enail2@mail.ru", "name2");
        List<User> participants = new ArrayList<>();
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minusHours(1).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                user, new Location(1L, 1.0f, 2.0f),
                false, 1, null, true, EventState.PUBLISHED, "title", 2,
                participants);
        ParticipationRequest request = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.REJECTED);
        ParticipationRequest requestSaved = new ParticipationRequest(LocalDateTime.now().minusMinutes(30).toEpochSecond(ZoneOffset.UTC),
                event, 1L, requestor, RequestStatus.REJECTED);
        when(userService.findById(1L))
                .thenReturn(user);
        when(eventService.findById(1L))
                .thenReturn(event);
        when(repository.findById(1L))
                .thenReturn(Optional.of(request));
        assertThrows(StatusException.class, () -> {
            ParticipationRequestDto result = service.rejectRequest(1L, 1L, 1L);
        });
    }
}