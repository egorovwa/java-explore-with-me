package ru.practicum.ewmmainservice.adminService.event.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.exceptions.EventStatusException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmmainservice.utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminEventServiceImplTest {
    @Mock
    private AdminEwentRepository repository;
    @Mock
    private CategoryService categoryService;


    @Mock
    private LocationDtoMaper locationDtoMaper;
    @Mock
    private EventDtoMaper eventDtoMaper;
    @InjectMocks
    AdminEventServiceImpl service;
    DateTimeFormatter formatter = Utils.getDateTimeFormater();
    Event event;
    @BeforeEach
    void setup(){
        event = new Event(1L,"anatation", new Category(1L,"category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L,"email@mail.ru", "name"), new Location(1L,1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L,"email2@mail.ru", "name2")));
    }

    @Test
    void test1_findByCategoryId() {
        when(repository.findAllByCategoryId(1L))
                .thenReturn(List.of(new Event()));
        Collection<Event> events = service.findByCategoryId(1L);
        verify(repository, times(1)).findAllByCategoryId(1L);
    }

    @Test
    void test2_1updateEventRequest() { // TODO: 22.09.2022 на потом
    }

    @Test
    void test3_1publishEvent() throws NotFoundException, EventStatusException, IllegalTimeException {
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));
        Event saved = new Event(1L,"anatation", new Category(1L,"category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L,"email@mail.ru", "name"), new Location(1L,1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L,"email2@mail.ru", "name2")));
        saved.setPublishedOn(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        saved.setState(EventState.PUBLISHED);
        when(repository.save(saved))
                .thenReturn(saved);
        service.publishEvent(1L);
        verify(repository, times(1)).save(saved);

    }
    @Test
    void test3_2publishEvent_whenStatusNotWaiting() throws NotFoundException, EventStatusException, IllegalTimeException {
        Event notWaiting = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(notWaiting));
assertThrows(EventStatusException.class, ()-> service.publishEvent(1L));

    }
    @Test
    void test3_3publishEvent_whenTimeNow() throws NotFoundException, EventStatusException, IllegalTimeException {
        Event timeNow = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(timeNow));
        assertThrows(IllegalTimeException.class, ()-> service.publishEvent(1L));

    }
    @Test
    void test4_1rejectEvent() throws NotFoundException, EventStatusException, IllegalTimeException {
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));
        Event saved = new Event(1L,"anatation", new Category(1L,"category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L,"email@mail.ru", "name"), new Location(1L,1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L,"email2@mail.ru", "name2")));
        saved.setState(EventState.CANCELLED);
        when(repository.save(saved))
                .thenReturn(saved);
        service.rejectEvent(1L);
        verify(repository, times(1)).save(saved);

    }
    @Test
    void test4_2rejectEvent_whenStatusNotWaiting() throws NotFoundException, EventStatusException, IllegalTimeException {
        Event notWaiting = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELLED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(notWaiting));
        assertThrows(EventStatusException.class, ()-> service.rejectEvent(1L));

    }
    @Test
    void test4_3rejectEvent_whenTimeNow() throws NotFoundException, EventStatusException, IllegalTimeException {
        Event timeNow = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.WAITING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(timeNow));
        assertThrows(IllegalTimeException.class, ()-> service.rejectEvent(1L));

    }

}