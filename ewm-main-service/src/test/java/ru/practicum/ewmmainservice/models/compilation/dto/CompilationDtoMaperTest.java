package ru.practicum.ewmmainservice.models.compilation.dto;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import com.example.evmdtocontract.dto.event.EventShortDto;
import com.example.evmdtocontract.dto.event.EventState;
import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.models.compilation.Compilation;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilationDtoMaperTest {
    private final CompilationDtoMaper dtoMaper = new CompilationDtoMaper(new EventDtoMaper(new UserDtoMapper()
            , new LocationDtoMaper(), new CategoryDtoMaper()));
    private final EventDtoMaper eventDtoMaper = new EventDtoMaper(new UserDtoMapper(), new LocationDtoMaper(), new CategoryDtoMaper());

    @Test
    void toDto() {
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
        Compilation compilation = new Compilation(List.of(event, event2), 1L,
                true, "titleCompilation");
        EventShortDto eventShortDto1 = eventDtoMaper.toShortDto(event);
        EventShortDto eventShortDto2 = eventDtoMaper.toShortDto(event2);
        CompilationDto resultDto = new CompilationDto(List.of(eventShortDto1, eventShortDto2), 1L, true,
                "titleCompilation");
        assertEquals(dtoMaper.toDto(compilation), resultDto);
    }
}