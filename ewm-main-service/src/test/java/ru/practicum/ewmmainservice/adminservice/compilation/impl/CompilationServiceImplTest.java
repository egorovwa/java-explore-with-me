package ru.practicum.ewmmainservice.adminservice.compilation.impl;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import com.example.evmdtocontract.dto.compilation.NewCompilationDto;
import com.example.evmdtocontract.dto.event.EventState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewmmainservice.adminservice.compilation.CompilationRepository;
import ru.practicum.ewmmainservice.adminservice.event.AdminEventService;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotRequiredException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.models.compilation.Compilation;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDtoMaper;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompilationServiceImplTest {
    final EventDtoMaper eventDtoMaper = new EventDtoMaper(new UserDtoMapper(), new LocationDtoMaper(), new CategoryDtoMaper());
    @InjectMocks
    CompilationServiceImpl service;
    @Mock
    private CompilationRepository repository;
    @Mock
    private AdminEventService eventService;
    @Mock
    private CompilationDtoMaper dtoMaper = new CompilationDtoMaper(eventDtoMaper);

    @Test
    void test1_1createCompilation() throws NotFoundException, FiledParamNotFoundException {
        NewCompilationDto newCompilationDto = new NewCompilationDto(List.of(1L, 2L), true, "title");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event, event2), null, true, "title");
        when(eventService.findById(1L))
                .thenReturn(event);
        when(eventService.findById(2L))
                .thenReturn(event2);
        CompilationDto result = service.createCompilation(newCompilationDto);
        verify(repository, times(1)).save(compilation);
    }

    @Test
    void test1_2createCompilation_whenEventNotFound() throws NotFoundException {
        NewCompilationDto newCompilationDto = new NewCompilationDto(List.of(1L, 2L), true, "title");
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event, event2), null, true, "title");
        when(eventService.findById(1L))
                .thenReturn(event);
        when(eventService.findById(2L))
                .thenThrow(NotFoundException.class);
        assertThrows(FiledParamNotFoundException.class, () -> {
            CompilationDto result = service.createCompilation(newCompilationDto);
            verify(repository, times(0)).save(any());
        });
    }

    @Test
    void test2_1Compilation() throws NotFoundException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event, event2), null, true, "title");

        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        service.deleteCompilation(1L);
        verify(repository, times(1)).deleteById(1L);

    }

    @Test
    void test2_1Compilation_whenNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.deleteCompilation(1L);
        });
    }

    @Test
    void test3_1findById() throws NotFoundException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event, event2), null, true, "title");
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        assertEquals(service.findById(1L), compilation);
    }

    @Test
    void test3_2findById_wheNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            Compilation compilation = service.findById(1L);
        });
    }

    @Test
    void test4_1deleteEventFromCompilation() throws NotFoundException, FiledParamNotFoundException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Collection<Event> comp1 = new ArrayList<>();
        comp1.add(event);
        comp1.add(event2);
        Collection<Event> comp2 = new ArrayList<>();
        comp2.add(event);

        Compilation compilation = new Compilation(comp1, null, true, "title");
        Compilation compilationSaved = new Compilation(comp2, null, true, "title");

        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        service.deleteEventFromCompilation(1L, 2L);
        verify(repository, times(1)).save(compilationSaved);

    }

    @Test
    void test4_2deleteEventFromCompilation_whenEventNotFound() {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event, event2), null, true, "title");
        Compilation compilationSaved = new Compilation(List.of(event), null, true, "title");

        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        assertThrows(FiledParamNotFoundException.class, () -> {
            service.deleteEventFromCompilation(1L, 3L);
        });
        verify(repository, times(0)).save(compilationSaved);
    }

    @Test
    void test4_3deleteEventFromCompilation_whenCompilationNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.deleteEventFromCompilation(1L, 3L);
        });
        verify(repository, times(0)).save(any());
    }

    @Test
    void test5_1addEventToCompilation() throws NotFoundException, NotRequiredException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Collection<Event> comp1 = new ArrayList<>();
        comp1.add(event);

        Collection<Event> comp2 = new ArrayList<>();
        comp2.add(event);
        comp2.add(event2);
        Compilation compilation = new Compilation(comp1, 1L, true, "title");
        Compilation compilationSaved = new Compilation(comp2, 1L, true, "title");

        when(eventService.findById(2L))
                .thenReturn(event2);
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        service.addEventToCompilation(1L, 2L);
        verify(repository, times(1)).save(compilationSaved);
    }

    @Test
    void test5_1addEventToCompilation_whenCompilationContantEvent() throws NotFoundException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event, event2), 1L, true, "title");

        when(eventService.findById(2L))
                .thenReturn(event2);
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        assertThrows(NotRequiredException.class, () -> {
            service.addEventToCompilation(1L, 2L);
        });
        verify(repository, times(0)).save(any());
    }

    @Test
    void test5_1addEventToCompilation_whenCompilationNotFound() {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));

        Compilation compilation = new Compilation(List.of(event), 1L, true, "title");
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.addEventToCompilation(1L, 2L);
        });
        verify(repository, times(0)).save(any());
    }

    @Test
    void test5_2addEventToCompilation_whenEventNotFound() throws NotFoundException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event), 1L, true, "title");

        when(eventService.findById(2L))
                .thenThrow(NotFoundException.class);
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        assertThrows(NotFoundException.class, () -> {
            service.addEventToCompilation(1L, 2L);
        });
        verify(repository, times(0)).save(any());
    }

    @Test
    void test6_1deletePinned() throws NotRequiredException, NotFoundException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event), 1L, true, "title");
        Compilation compilationSaved = new Compilation(List.of(event), 1L, false, "title");
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        service.deletePinned(1L);
        verify(repository, times(1)).save(compilationSaved);
    }

    @Test
    void test6_2deletePinned_whenNotPinned() {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event), 1L, false, "title");
        Compilation compilationSaved = new Compilation(List.of(event), 1L, false, "title");
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        assertThrows(NotRequiredException.class, () -> {
            service.deletePinned(1L);
        });
    }

    @Test
    void test6_3deletePinned_whenCompilationNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.deletePinned(1L);
        });
    }

    @Test
    void test7_1compilationPinned() throws NotRequiredException, NotFoundException {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event), 1L, false, "title");
        Compilation compilationSaved = new Compilation(List.of(event), 1L, true, "title");
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        service.addPinned(1L);
        verify(repository, times(1)).save(compilationSaved);
    }

    @Test
    void test7_2compilationPinned_whenPinned() {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Event event2 = new Event(2L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusHours(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), new Location(1L, 1.0f, 2.0f),
                false, 10, null, true, EventState.CANCELED, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Compilation compilation = new Compilation(List.of(event), 1L, true, "title");
        Compilation compilationSaved = new Compilation(List.of(event), 1L, false, "title");
        when(repository.findById(1L))
                .thenReturn(Optional.of(compilation));
        assertThrows(NotRequiredException.class, () -> {
            service.addPinned(1L);
        });
    }

    @Test
    void test7_3deletePinned_whenCompilationNotFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.addPinned(1L);
        });
    }
}