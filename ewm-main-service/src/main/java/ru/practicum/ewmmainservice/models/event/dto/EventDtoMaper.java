package ru.practicum.ewmmainservice.models.event.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMaper;
import ru.practicum.ewmmainservice.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor
public class EventDtoMaper {
    private final UserDtoMaper userDtoMaper;
    private final LocationDtoMaper locationDtoMaper;
    private final DateTimeFormatter formatter = Utils.getDateTimeFormater();

    public Event fromNewDto(NewEventDto newEventDto, Category category, User user, Location location) {

        return new Event(newEventDto.getAnnotation(),
                category,
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                newEventDto.getDescription(),
                LocalDateTime.parse(newEventDto.getEventDate(), formatter).toEpochSecond(ZoneOffset.UTC),
                user,
                location,
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(), null,
                newEventDto.getRequestModeration(),
                EventState.WAITING,
                newEventDto.getTitle());
    }

    public EventFullDto toFulDto(Event event) {
        String publishedOn = null;
        if (event.getPublishedOn() != null) {
            publishedOn = formatter.format(LocalDateTime.ofEpochSecond(event.getPublishedOn(),
                    0, ZoneOffset.UTC));
        }
        return new EventFullDto(event.getId(),
                event.getAnnotation(),
                event.getCategory(),
                event.getParticipants().size(),
                formatter.format(LocalDateTime.ofEpochSecond(event.getCreatedOn(), 0, ZoneOffset.UTC)),
                event.getDescription(),
                formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)),
                userDtoMaper.toShortDto(event.getInitiator()),
                locationDtoMaper.toDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                publishedOn,
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews());
    }
}
