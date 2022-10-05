package ru.practicum.ewmmainservice.adminService.event.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.parameters.ParametersAdminFindEvent;
import ru.practicum.ewmmainservice.models.parameters.ParametersValidator;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminEventServiceImplTest {
    @Mock
    LocationService locationService;
    @Mock
    ParametersValidator validator;
    @InjectMocks
    AdminEventServiceImpl service;
    final DateTimeFormatter formatter = Utils.getDateTimeFormater();
    Event event;
    @Mock
    private AdminEwentRepository repository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private LocationDtoMaper locationDtoMaper;
    @Mock
    private EventDtoMaper eventDtoMaper;

    @BeforeEach
    void setup() {
        event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
    }

    @Test
    void test1_findByCategoryId() {
        when(repository.findAllByCategoryId(1L))
                .thenReturn(List.of(new Event()));
        Collection<Event> events = service.findByCategoryId(1L);
        verify(repository, times(1)).findAllByCategoryId(1L);
    }

    @Test
    void test2_1updateEventRequest() throws NotFoundException, FiledParamNotFoundException {
        AdminUpdateEventRequest request = new AdminUpdateEventRequest();
        request.setAnnotation("update annotation");
        request.setCategory(2L);
        request.setDescription("update description");
        request.setEventDate("2022-10-01 00:00:00");
        request.setLocation(new LocationDto(1f, 2f));
        request.setPaid(true);
        request.setParticipantLimit(100);
        request.setRequestModeration(false);
        request.setTitle("updated");
        Event saved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        saved.setAnnotation("update annotation");
        saved.setCategory(new Category(2L, "update"));
        saved.setDescription("update description");
        saved.setEventDate(LocalDateTime.parse("2022-10-01 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC));
        saved.setLocation(new Location(2L, 1f, 2f));
        saved.setPaid(true);
        saved.setParticipantLimit(100);
        saved.setRequestModeration(false);
        saved.setTitle("updated");
        when(categoryService.findByid(2L))
                .thenReturn(new Category(2L, "update"));
        when(locationService.save(new LocationDto(1f, 2f)))
                .thenReturn(new Location(2L, 1f, 2f));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));
        when(repository.save(saved))
                .thenReturn(saved);
        service.updateEventRequest(1L, request);
    }

    @Test
    void test2_2updateEventRequest_whenEventNotFound() {
        AdminUpdateEventRequest request = new AdminUpdateEventRequest();
        request.setAnnotation("update annotation");
        request.setCategory(2L);
        request.setDescription("update description");
        request.setEventDate("2022-10-01 00:00:00");
        request.setLocation(new LocationDto(1f, 2f));
        request.setPaid(true);
        request.setParticipantLimit(100);
        request.setRequestModeration(false);
        request.setTitle("updated");
        Event saved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        saved.setAnnotation("update annotation");
        saved.setCategory(new Category(2L, "update"));
        saved.setDescription("update description");
        saved.setEventDate(LocalDateTime.parse("2022-10-01 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC));
        saved.setLocation(new Location(2L, 1f, 2f));
        saved.setPaid(true);
        saved.setParticipantLimit(100);
        saved.setRequestModeration(false);
        saved.setTitle("updated");

        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.updateEventRequest(1L, request);
        });
    }

    @Test
    void test2_3updateEventRequest_whenCategoryNotFound() throws NotFoundException {
        AdminUpdateEventRequest request = new AdminUpdateEventRequest();
        request.setAnnotation("update annotation");
        request.setCategory(2L);
        request.setDescription("update description");
        request.setEventDate("2022-10-01 00:00:00");
        request.setLocation(new LocationDto(1f, 2f));
        request.setPaid(true);
        request.setParticipantLimit(100);
        request.setRequestModeration(false);
        request.setTitle("updated");
        Event saved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        saved.setAnnotation("update annotation");
        saved.setCategory(new Category(2L, "update"));
        saved.setDescription("update description");
        saved.setEventDate(LocalDateTime.parse("2022-10-01 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC));
        saved.setLocation(new Location(2L, 1f, 2f));
        saved.setPaid(true);
        saved.setParticipantLimit(100);
        saved.setRequestModeration(false);
        saved.setTitle("updated");
        when(categoryService.findByid(2L))
                .thenThrow(NotFoundException.class);

        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));

        assertThrows(FiledParamNotFoundException.class, () -> {

            service.updateEventRequest(1L, request);
        });
    }

    @Test
    void test3_1publishEvent() throws NotFoundException, StatusException, IllegalTimeException {
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));
        Event saved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        saved.setPublishedOn(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        saved.setState(EventState.PUBLISHED);
        when(repository.save(saved))
                .thenReturn(saved);
        service.publishEvent(1L);
        verify(repository, times(1)).save(saved);

    }

    @Test
    void test3_2publishEvent_whenStatusNotWaiting() {
        Event notWaiting = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PUBLISHED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(notWaiting));
        assertThrows(StatusException.class, () -> service.publishEvent(1L));

    }

    @Test
    void test3_3publishEvent_whenNotFound() throws NotFoundException, StatusException, IllegalTimeException {
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));
        Event saved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        saved.setPublishedOn(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        saved.setState(EventState.PUBLISHED);
        when(repository.save(saved))
                .thenReturn(saved);
        service.publishEvent(1L);


    }

    @Test
    void test3_3publishEvent_whenTimeNow() {
        Event timeNow = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(timeNow));
        assertThrows(IllegalTimeException.class, () -> service.publishEvent(1L));

    }

    @Test
    void test4_1rejectEvent() throws NotFoundException, StatusException, IllegalTimeException {
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));
        Event saved = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        saved.setState(EventState.CANCELED);
        when(repository.save(saved))
                .thenReturn(saved);
        service.rejectEvent(1L);
        verify(repository, times(1)).save(saved);

    }

    @Test
    void test4_2rejectEvent_whenStatusNotWaiting() {
        Event notWaiting = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(notWaiting));
        assertThrows(StatusException.class, () -> service.rejectEvent(1L));

    }

    @Test
    void test4_3rejectEvent_whenTimeNow() {
        Event timeNow = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(timeNow));
        assertThrows(IllegalTimeException.class, () -> service.rejectEvent(1L));
    }

    @Test
    void test5_1findAllEvents() throws IncorrectPageValueException, NotValidParameterException, IllegalTimeException {
        Event timeNow = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Long[] usersId = {1L, 2L, 3L};
        Long[] catId = {1L};
        String start = formatter.format(LocalDateTime.now());
        String end = formatter.format(LocalDateTime.now().plusHours(1));
        String[] states = {"PENDING"};
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId, start, end, 0, 10);
        Page<Event> eventPage = new PageImpl<>(List.of(event));
        when(repository.findForAdmin(List.of(1L, 2L, 3L),
                List.of(1L), List.of(EventState.PENDING),
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC), PageParam.createPageable(0, 10)))
                .thenReturn(eventPage);
        List<EventFullDto> result = service.findAllEvents(param);
        verify(repository, times(1)).findForAdmin(List.of(1L, 2L, 3L),
                List.of(1L), List.of(EventState.PENDING),
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC), PageParam.createPageable(0, 10));

    }

    @Test
    void test5_2findAllEvents_whenParameterIncorrect() throws IncorrectPageValueException, NotValidParameterException, IllegalTimeException {
        Event timeNow = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Long[] usersId = {1L, 2L, 3L};
        Long[] catId = {1L};
        String start = formatter.format(LocalDateTime.now());
        String end = formatter.format(LocalDateTime.now().plusHours(1));
        String[] states = {"PENDING"};
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId, start, end, 0, 10);
        Page<Event> eventPage = new PageImpl<>(List.of(event));
        doThrow(NotValidParameterException.class).when(validator).adminFindEvents(param);
        assertThrows(NotValidParameterException.class, () -> {
            List<EventFullDto> result = service.findAllEvents(param);
        });
        verify(repository, times(0)).findForAdmin(anyList(), anyList(), anyList(), anyLong(), anyLong(), any());

    }

    @Test
    void test6_findById() throws NotFoundException {
        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(event));
        service.findById(1L);
        verify(repository).findById(1L);
    }

    @Test
    void test7_save() {
        when(repository.save(event))
                .thenReturn(event);
        service.save(event);
        verify(repository).save(event);
    }

}