package ru.practicum.ewmmainservice.models.event.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMaper;
import ru.practicum.ewmmainservice.models.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


class EventDtoMaperTest {
    private final LocationDtoMaper locationDtoMaper = new LocationDtoMaper();
    private final EventDtoMaper maper = new EventDtoMaper(new UserDtoMaper(), locationDtoMaper);

    @Test
    void fromNewDto() {
        LocalDateTime eventDate = LocalDateTime.of(2022, 9, 21, 22, 11, 0);
        User user = new User(1L, "email@mail", "name");
        Category category = new Category(1L, "category");
        LocationDto locationDto = new LocationDto();
        locationDto.setLat(1.0f);
        locationDto.setLon(2.0f);

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
        Event event = maper.fromNewDto(newEventDto, category, user, locationDtoMaper.fromDto(locationDto));


        assertThat(event.getAnnotation(), is("anatation"));
        assertThat(event.getCategory(), is(category));
        assertThat(event.getDescription(), is("Description"));
        assertThat(event.getEventDate(), is(eventDate.toEpochSecond(ZoneOffset.UTC)));
        assertThat(event.getLocation(), is(locationDtoMaper.fromDto(locationDto))); // TODO: 21.09.2022  location dto maper
        assertThat(event.getPaid(), is(true));
        assertThat(event.getParticipantLimit(), is(10));
        assertThat(event.getRequestModeration(), is(false));
        assertThat(event.getTitle(), is("title"));
    }

    @Test
    void toFulDto() {
        Category category = new Category(1l, "category");
        User user = new User(1L, "email@mail.ru", "name");
        UserShortDto userShortDto = new UserShortDto(1L, "name");
        Location location = new Location(1L, 1.0f, 2.0f);
        LocationDto locationDto = new LocationDto(1.0f, 2.0f);
        List<User> participans = List.of(new User(2L, "emai@rrr.ru", "name2"),
                new User(3L, "sss@sss.fff", "name3"));
        ParticipationRequest participationRequest = new ParticipationRequest();
        Event event = new Event(1l,
                "anatation",
                category,
                LocalDateTime.of(2022, 9, 6, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //2022-09-06 11:00:23
                "Description",
                LocalDateTime.of(2022, 9, 7, 11, 0, 23).toEpochSecond(ZoneOffset.UTC),
                user,
                location,
                true,
                10,
                LocalDateTime.of(2022, 9, 11, 16, 11, 0).toEpochSecond(ZoneOffset.UTC),
                true,
                EventState.PUBLISHED,
                "title",
                5,
                participans);
        EventFullDto dto = maper.toFulDto(event);
        assertThat(dto.getId(), is(1L));
        assertThat(dto.getAnnotation(), is("anatation"));
        assertThat(dto.getCategory(), is(category));
        assertThat(dto.getCreatedOn(), is("2022-09-06 11:00:23"));
        assertThat(dto.getDescription(), is("Description"));
        assertThat(dto.getEventDate(), is("2022-09-07 11:00:23"));
        assertThat(dto.getInitiator(), is(userShortDto));
        assertThat(dto.getLocation(), is(locationDto));
        assertThat(dto.getPaid(), is(true));
        assertThat(dto.getParticipantLimit(), is(10));
        assertThat(dto.getPublishedOn(), is("2022-09-11 16:11:00"));
        assertThat(dto.getRequestModeration(), is(true));
        assertThat(dto.getState(), is(EventState.PUBLISHED));
        assertThat(dto.getTitle(), is("title"));
        assertThat(dto.getViews(), is(5));
        assertThat(dto.getConfirmedRequests(), is(2));
    }

    @Test
    void toShortDto() {

        Category category = new Category(1l, "category");
        User user = new User(1L, "email@mail.ru", "name");
        UserShortDto userShortDto = new UserShortDto(1L, "name");
        Location location = new Location(1L, 1.0f, 2.0f);
        LocationDto locationDto = new LocationDto(1.0f, 2.0f);
        List<User> participans = List.of(new User(2L, "emai@rrr.ru", "name2"),
                new User(3L, "sss@sss.fff", "name3"));
        ParticipationRequest participationRequest = new ParticipationRequest();
        Event event = new Event(1l,
                "anatation",
                category,
                LocalDateTime.of(2022, 9, 6, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //2022-09-06 11:00:23
                "Description",
                LocalDateTime.of(2022, 9, 7, 11, 0, 23).toEpochSecond(ZoneOffset.UTC),
                user,
                location,
                true,
                10,
                LocalDateTime.of(2022, 9, 11, 16, 11, 0).toEpochSecond(ZoneOffset.UTC),
                true,
                EventState.PUBLISHED,
                "title",
                5,
                participans);
        EventShortDto dto = maper.toShortDto(event);
        assertThat(dto.getId(), is(1L));
        assertThat(dto.getAnnotation(), is("anatation"));
        assertThat(dto.getCategory(), is(category));
        assertThat(dto.getEventDate(), is("2022-09-07 11:00:23"));
        assertThat(dto.getInitiator(), is(userShortDto));
        assertThat(dto.getPaid(), is(true));
        assertThat(dto.getTitle(), is("title"));
        assertThat(dto.getViews(), is(5));
        assertThat(dto.getConfirmedRequests(), is(2));

    }
}