package ru.practicum.ewmmainservice.models.event.dto;

import com.example.evmdtocontract.dto.event.EventFullDto;
import com.example.evmdtocontract.dto.event.EventShortDto;
import com.example.evmdtocontract.dto.event.EventState;
import com.example.evmdtocontract.dto.event.NewEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMapper;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Component
@RequiredArgsConstructor
public class EventDtoMaper {
    private final UserDtoMapper userDtoMapper;
    private final LocationDtoMaper locationDtoMaper;
    private final CategoryDtoMaper categoryDtoMaper;
    private final DateTimeFormatter formatter = Utils.getDateTimeFormatter();

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
                EventState.PENDING,
                newEventDto.getTitle(),
                new ArrayList<>());
    }

    public EventFullDto toFulDto(Event event) {
        String publishedOn = null;
        if (event.getPublishedOn() != null) {
            publishedOn = formatter.format(LocalDateTime.ofEpochSecond(event.getPublishedOn(),
                    0, ZoneOffset.UTC));
        }
        return new EventFullDto(event.getId(),
                event.getAnnotation(),
                categoryDtoMaper.toDto(event.getCategory()),
                event.getParticipants().size(),
                formatter.format(LocalDateTime.ofEpochSecond(event.getCreatedOn(), 0, ZoneOffset.UTC)),
                event.getDescription(),
                formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)),
                userDtoMapper.toShortDto(event.getInitiator()),
                locationDtoMaper.toDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                publishedOn,
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews());
    }

    public EventShortDto toShortDto(Event event) {
        return new EventShortDto(event.getId(),
                event.getAnnotation(),
                categoryDtoMaper.toDto(event.getCategory()),
                formatter.format(LocalDateTime.ofEpochSecond(event.getEventDate(), 0, ZoneOffset.UTC)),
                event.getParticipants().size(),
                userDtoMapper.toShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }
}
