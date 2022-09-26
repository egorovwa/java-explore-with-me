package ru.practicum.ewmmainservice.privateservise.event.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.event.dto.UpdateEventRequest;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMaper;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;
import ru.practicum.ewmmainservice.utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(EventDtoMaper.class)
class PrivateAdminEventServiceImplTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserAdminService userAdminService;
    @Mock
    private PrivateEventRepository repository;
    @Mock
    private final EventDtoMaper eventDtoMaper = new EventDtoMaper(new UserDtoMaper(), new LocationDtoMaper());
    @Mock
    private LocationService locationService;
    @InjectMocks
    PrivateEventServiceImpl service;
    DateTimeFormatter formater = Utils.getDateTimeFormater();

    PrivateAdminEventServiceImplTest() throws FiledParamNotFoundException {
    }

    @Test
    void test1_1createEvent() throws NotFoundException, FiledParamNotFoundException {
        LocationDto locationDto = new LocationDto(1.0f, 2.0f);

        NewEventDto newEventDto = new NewEventDto();
        newEventDto.setAnnotation("anatation");
        newEventDto.setCategory(1L);
        newEventDto.setDescription("Description");
        newEventDto.setEventDate("2022-09-21 22:11:00");
        newEventDto.setLocation(locationDto);
        newEventDto.setPaid(true);
        newEventDto.setParticipantLimit(10);
        newEventDto.setRequestModeration(false);
        newEventDto.setTitle("title");
        when(userAdminService.findById(1L))
                .thenReturn(new User(1L, "email", "name"));
        when(categoryService.findByid(1L))
                .thenReturn(new Category(1L, "category"));
        when(locationService.findByLatAndLon(1.0f, 2.0f))
                .thenReturn(Optional.of(new Location(1l, 1.0f, 2.0f)));
        EventFullDto event = eventDtoMaper.toFulDto(eventDtoMaper.fromNewDto(newEventDto,
                new Category(1L, "category"), new User(1L, "email", "name"),
                new Location(1l, 1.0f, 2.0f)));
        assertThat(service.createEvent(1L, newEventDto), is(event));
    }

    @Test
    void test1_2createEvent_whenAnyFiledNotFound() throws NotFoundException {
        LocationDto locationDto = new LocationDto(1.0f, 2.0f);

        NewEventDto newEventDto = new NewEventDto();
        newEventDto.setAnnotation("anatation");
        newEventDto.setCategory(1L);
        newEventDto.setDescription("Description");
        newEventDto.setEventDate("2022-09-21 22:11:00");
        newEventDto.setLocation(locationDto);
        newEventDto.setPaid(true);
        newEventDto.setParticipantLimit(10);
        newEventDto.setRequestModeration(false);
        newEventDto.setTitle("title");
        when(userAdminService.findById(1L))
                .thenThrow(NotFoundException.class);
        assertThrows(FiledParamNotFoundException.class,
                () -> service.createEvent(1L, newEventDto));
    }

    @Test
    void test2_1patchEvent() throws NotFoundException, StatusException, IllegalTimeException, IlegalUserIdException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("UpdatedAnatation");
        request.setCategory(2L);
        request.setDescription("updated d");
        request.setEventDate(formater.format(LocalDateTime.now().plusDays(2)));
        request.setPaid(true);
        request.setParticipantLimit(50);
        request.setTitle("updated Title");
        request.setEventId(1L);
        Event updated = new Event(1L, "UpdatedAnatation", new Category(2L, "updated"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "updated d", LocalDateTime.now().plusDays(2).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                true, 50, null, true, EventState.WAITING, "updated Title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        when(categoryService.findByid(2L))
                .thenReturn(new Category(2L, "updated"));
        when(repository.findById(1L))
                .thenReturn(Optional.of(event));
        EventFullDto res = service.patchEvent(1L, request);
        verify(repository, times(1)).save(updated);
    }

    @Test
    void test2_2patchEvent_whenUserNotInitials() throws NotFoundException, StatusException, IllegalTimeException, IlegalUserIdException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(2L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("UpdatedAnatation");
        request.setCategory(2L);
        request.setDescription("updated d");
        request.setEventDate(formater.format(LocalDateTime.now().plusDays(2)));
        request.setPaid(true);
        request.setParticipantLimit(50);
        request.setTitle("updated Title");
        request.setEventId(1L);
        Event updated = new Event(1L, "UpdatedAnatation", new Category(2L, "updated"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "updated d", LocalDateTime.now().plusDays(2).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                true, 50, null, true, EventState.WAITING, "updated Title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        when(repository.findById(1L))
                .thenReturn(Optional.of(event));
        assertThrows(IlegalUserIdException.class, () -> {
            EventFullDto res = service.patchEvent(1L, request);
        });
    }

    @Test
    void test2_3patchEvent_whenStaeIsPublished() throws NotFoundException, StatusException, IllegalTimeException, IlegalUserIdException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("UpdatedAnatation");
        request.setCategory(2L);
        request.setDescription("updated d");
        request.setEventDate(formater.format(LocalDateTime.now().plusDays(2)));
        request.setPaid(true);
        request.setParticipantLimit(50);
        request.setTitle("updated Title");
        request.setEventId(1L);
        Event updated = new Event(1L, "UpdatedAnatation", new Category(2L, "updated"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "updated d", LocalDateTime.now().plusDays(2).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                true, 50, null, true, EventState.WAITING, "updated Title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));


        when(repository.findById(1L))
                .thenReturn(Optional.of(event));
        assertThrows(StatusException.class, () -> {
            EventFullDto res = service.patchEvent(1L, request);
        });
    }

    @Test
    void test2_4patchEvent_incorrectrDate() throws NotFoundException, StatusException, IllegalTimeException, IlegalUserIdException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("UpdatedAnatation");
        request.setCategory(2L);
        request.setDescription("updated d");
        request.setEventDate(formater.format(LocalDateTime.now().plusDays(2)));
        request.setPaid(true);
        request.setParticipantLimit(50);
        request.setTitle("updated Title");
        request.setEventId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(event));
        assertThrows(IllegalTimeException.class, () -> {
            EventFullDto res = service.patchEvent(1L, request);
        });
    }

    @Test
    void test3_1eventСancellation() throws NotFoundException, StatusException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event cancelled = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELLED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        User user = new User(1L, "email@mail.ru", "name");
        when(repository.findById(1L))
                .thenReturn(Optional.of(event));
        when(userAdminService.findById(1L))
                .thenReturn(user);
        service.eventСancellation(1L, 1L);
        verify(repository, times(1)).save(cancelled);
    }

    @Test
    void test3_2eventСancellation_whenEwentStateNotWaiting() throws NotFoundException, StatusException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        User user = new User(1L, "email@mail.ru", "name");
        when(repository.findById(1L))
                .thenReturn(Optional.of(event));
        when(userAdminService.findById(1L))
                .thenReturn(user);
        assertThrows(StatusException.class, () -> {
            service.eventСancellation(1L, 1L);
        });
    }
}