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
import ru.practicum.ewmmainservice.exceptions.IlegalUserIdException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.StatusException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.event.dto.UpdateEventRequest;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMapper;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrivateEventController.class)
class PrivateEventControllerTest {
    private static final String API = "/users";
    private final LocationDtoMaper locationDtoMaper = new LocationDtoMaper();
    private final EventDtoMaper eventDtoMaper = new EventDtoMaper(new UserDtoMapper(), locationDtoMaper);
    @MockBean
    PrivateEventService service;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

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

    @Test
    void test2_1patchEvent() throws Exception {
        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("aaaaaaa aaaaaaa aaaaaaa aaaaaaa");
        mvc.perform(patch(API + "/{userId}/events", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        verify(service, times(1)).patchEvent(1L, request);
    }

    @Test
    void test2_2patchEvent_whenStatusException() throws Exception {
        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("aaaaaaa aaaaaaa aaaaaaa aaaaaaa");
        when(service.patchEvent(1L, request))
                .thenThrow(new StatusException("message"));
        mvc.perform(patch(API + "/{userId}/events", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.reason", is("For the requested operation the conditions are not met.")));

    }

    @Test
    void test2_3patchEvent_whenIllegalTime() throws Exception {
        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("aaaaaaa aaaaaaa aaaaaaa aaaaaaa");
        when(service.patchEvent(1L, request))
                .thenThrow(new IllegalTimeException("message", "TIME"));
        mvc.perform(patch(API + "/{userId}/events", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.reason", is("Time error.")));

    }

    @Test
    void test2_3patchEvent_whenIlegalUserIdException() throws Exception {
        UpdateEventRequest request = new UpdateEventRequest();
        request.setAnnotation("aaaaaaa aaaaaaa aaaaaaa aaaaaaa");
        when(service.patchEvent(1L, request))
                .thenThrow(new IlegalUserIdException(1L, 1L, "message"));
        mvc.perform(patch(API + "/{userId}/events", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.reason", is("The User does not have access to the requested Object.")));
    }

    @Test
    void test3_eventСancellation() throws Exception {
        mvc.perform(patch(API + "/{userId}/events/{eventId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).eventСancellation(1L, 1L);
    }

    @Test
    void test4_findEventForInitiator() throws Exception {
        mvc.perform(get(API + "/{userId}/events/{eventId}", 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, times(1)).findEventForInitiator(1L, 1L);
    }

    @Test
    void test5_findAllEventByInitiator() throws Exception {
        mvc.perform(get(API + "/{userId}/events", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).findAllEventByInitiator(1L);
    }

}