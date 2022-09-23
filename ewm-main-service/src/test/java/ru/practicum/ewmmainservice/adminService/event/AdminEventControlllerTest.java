package ru.practicum.ewmmainservice.adminService.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.ewmmainservice.exceptions.EventStatusException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminEventControlller.class)
class AdminEventControlllerTest {
    @MockBean
    AdminEventService service;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;
    private static String API = "/admin/events/";

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }


    @Test
    void test1_1updateEvent() throws Exception {
        AdminUpdateEventRequest request = new AdminUpdateEventRequest();
        mvc.perform(put(API + "/{eventId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service, times(1)).updateEventRequest(1L, request);
    }

    @Test
    void test2_2updateEvent_wheEventNotFouns() throws Exception {
        AdminUpdateEventRequest request = new AdminUpdateEventRequest();
        when(service.updateEventRequest(1L, request))
                .thenThrow(new NotFoundException("message", "param", "value", "class"));
        mvc.perform(put(API + "/{eventId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("message")));

    }

    @Test
    void test2_1_publishEvent() throws Exception {
        mvc.perform(patch(API + "/{eventId}/publish", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8));
        verify(service, times(1)).publishEvent(1L);
    }
    @Test
    void test2_2_publishEvent_whenStateNotWating() throws Exception {
        when(service.publishEvent(1L))
                .thenThrow(new EventStatusException("message"));
        mvc.perform(patch(API + "/{eventId}/publish", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(jsonPath("$.message", is("message")));

    }
    @Test
    void test3_1_rejectEvent() throws Exception {
        mvc.perform(patch(API + "/{eventId}/reject", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8));
        verify(service, times(1)).rejectEvent(1L);
    }
    @Test
    void test3_2_rejectEvent_whenStateNotWating() throws Exception {
        when(service.rejectEvent(1L))
                .thenThrow(new EventStatusException("message"));
        mvc.perform(patch(API + "/{eventId}/reject", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(jsonPath("$.message", is("message")));

    }

}