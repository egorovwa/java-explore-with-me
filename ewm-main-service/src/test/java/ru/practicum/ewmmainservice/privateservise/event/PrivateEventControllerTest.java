package ru.practicum.ewmmainservice.privateservise.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMaper;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrivateEventController.class)
class PrivateEventControllerTest {
    @MockBean
    PrivateEventService service;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;
    private static String API = "/users";
    private final LocationDtoMaper locationDtoMaper = new LocationDtoMaper();
    private final EventDtoMaper eventDtoMaper = new EventDtoMaper(new UserDtoMaper(), locationDtoMaper);

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }

    @Test
    void test1_1createEvent() throws Exception {
        User user = new User(1L, "Email@mail.com", "name");
        Category category = new Category(1L, "categoty");
        LocationDto locationDto = new LocationDto(1.0f, 2.0f);
        NewEventDto newEventDto = new NewEventDto();
        newEventDto.setAnnotation("annotation annotation annotation");
        newEventDto.setCategory(1L);
        newEventDto.setDescription("Description Description Description");
        newEventDto.setEventDate("2022-09-21 22:11:00");
        newEventDto.setLocation(locationDto);
        newEventDto.setPaid(true);
        newEventDto.setParticipantLimit(10);
        newEventDto.setRequestModeration(false);
        newEventDto.setTitle("title");
        mvc.perform(post("/users/1/events")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEventDto)))
                .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).createEvent(1L, newEventDto);
    }

    @Test
    void test1_2createEvent_whenFiledParamNotFoundException() throws Exception {
        User user = new User(1L, "Email@mail.com", "name");
        Category category = new Category(1L, "categoty");
        LocationDto locationDto = new LocationDto(1.0f, 2.0f);
        NewEventDto newEventDto = new NewEventDto();
        newEventDto.setAnnotation("annotation annotation annotation");
        newEventDto.setCategory(1L);
        newEventDto.setDescription("Description Description Description");
        newEventDto.setEventDate("2022-09-21 22:11:00");
        newEventDto.setLocation(locationDto);
        newEventDto.setPaid(true);
        newEventDto.setParticipantLimit(10);
        newEventDto.setRequestModeration(false);
        newEventDto.setTitle("title");
        when(service.createEvent(1L, newEventDto))
                .thenThrow(new FiledParamNotFoundException("message"));
        mvc.perform(post("/users/1/events")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEventDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("message")));
    }
    @Test
    void test1_3createEvent_anyFiledNotValid() throws Exception {
        User user = new User(1L, "Email@mail.com", "name");
        Category category = new Category(1L, "categoty");
        LocationDto locationDto = new LocationDto(1.0f, 2.0f);
        NewEventDto newEventDto = new NewEventDto();
        newEventDto.setAnnotation("annotation");
        newEventDto.setCategory(1L);
        newEventDto.setDescription("Description Description Description");
        newEventDto.setEventDate("2022-09-21 22:11:00");
        newEventDto.setLocation(locationDto);
        newEventDto.setPaid(true);
        newEventDto.setParticipantLimit(10);
        newEventDto.setRequestModeration(false);
        newEventDto.setTitle("title");
        mvc.perform(post("/users/1/events")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEventDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is("One or more fields are not valid.")));
        Mockito.verify(service, Mockito.times(0)).createEvent(1L, newEventDto);

    }
}