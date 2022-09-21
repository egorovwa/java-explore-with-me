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

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Component
@RequiredArgsConstructor
public class EventDtoMaper {
    UserDtoMaper userDtoMaper;
    LocationDtoMaper locationDtoMaper;

    public Event fromNewDto(NewEventDto newEventDto, Category category, User user, Location location){

        return new Event(newEventDto.getAnnotation(),
                category,
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                newEventDto.getDescription(),
                newEventDto.getEventDate().toEpochSecond(ZoneOffset.UTC),
                user,
                location,
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(), null,
                newEventDto.getRequestModeration(),
                EventState.WAITING,
                newEventDto.getTitle());
    }

    public EventFullDto toFulDto(Event event) {
        return new EventFullDto(event.getId(),
                event.getAnnotation(),
                event.getCategory(),
                event.getParticipants().size(),
                LocalDateTime.ofEpochSecond(event.getCreatedOn(),0,ZoneOffset.UTC).toString(),
                event.getDescription(),
                LocalDateTime.ofEpochSecond(event.getEventDate(),0, ZoneOffset.UTC).toString(),
                userDtoMaper.toShortDto(event.getInitiator()),
                locationDtoMaper.toDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                LocalDateTime.ofEpochSecond(event.getPublishedOn(),0, ZoneOffset.UTC).toString(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews());
    }
}
