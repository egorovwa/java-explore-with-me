package ru.practicum.ewmmainservice.models.participationrequest.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.participationrequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.participationrequest.RequestStatus;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticipationRequestDtoMaperTest {
    final DateTimeFormatter formatter = Utils.getDateTimeFormatter();
    final ParticipationRequestDtoMaper dtoMaper = new ParticipationRequestDtoMaper();
    Location location = new Location(1L, "location", 83.1454, 53.4545, 5000, null, new ArrayList<>(), true);

    @Test
    void toDto() {
        Event event = new Event(1L, "anatation", new Category(1L, "category"),
                LocalDateTime.now().minus(Duration.ofMinutes(60)).toEpochSecond(ZoneOffset.UTC),
                "description", LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC),
                new User(1L, "email@mail.ru", "name"), location,
                false, 10, null, true, EventState.PENDING, "title", 2,
                List.of(new User(2L, "email2@mail.ru", "name2")));
        Long created = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        String dtoCreated = formatter.format(LocalDateTime.ofEpochSecond(created, 0, ZoneOffset.UTC));
        User user = new User(1L, "email@mail.ru", "name");
        ParticipationRequest request = new ParticipationRequest(created, event, 1L, user, RequestStatus.PENDING);
        ParticipationRequestDto dto = new ParticipationRequestDto(dtoCreated, 1L, 1L, 1L,
                RequestStatus.PENDING);
        assertEquals(dtoMaper.toDto(request), dto);
    }
}