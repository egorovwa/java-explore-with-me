package ru.practicum.ewmmainservice.models.event.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


class EventDtoMaperTest {
    EventDtoMaper maper = new EventDtoMaper();
    LocationDtoMaper locationDtoMaper = new LocationDtoMaper();

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
        newEventDto.setEventDate(eventDate);
        newEventDto.setLocation(locationDto);
        newEventDto.setPaid(true);
        newEventDto.setParticipantLimit(10);
        newEventDto.setRequestModeration(false);
        newEventDto.setTitle("title");
        Event event = maper.fromNewDto(newEventDto, category, user, locationDtoMaper.fromDto(locationDto));


        assertThat(event.getAnnotation(), is("anatation"));
        assertThat(event.getCategory(),is(category));
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
    }
}