package ru.practicum.ewmmainservice.adminservice.event;

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
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.StatusException;
import ru.practicum.ewmmainservice.models.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.models.parameters.ParametersAdminFindEvent;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminEventController.class)
    class AdminEventControllerTest {
    private static final String API = "/admin/events/";
    @MockBean
    AdminEventService service;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

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
    void test2_2updateEvent_wheEventNotFound() throws Exception {
        AdminUpdateEventRequest request = new AdminUpdateEventRequest();
        when(service.updateEventRequest(1L, request))
                .thenThrow(new NotFoundException("param", "value", "class"));
        mvc.perform(put(API + "/{eventId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("class param value not found.")));

    }

    @Test
    void test2_1_publishEvent() throws Exception {
        mvc.perform(patch(API + "/{eventId}/publish", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8));
        verify(service, times(1)).publishEvent(1L);
    }

    @Test
    void test2_2_publishEvent_whenStateNotWaiting() throws Exception {
        when(service.publishEvent(1L))
                .thenThrow(new StatusException("message"));
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
    void test3_2_rejectEvent_whenStateNotWaiting() throws Exception {
        when(service.rejectEvent(1L))
                .thenThrow(new StatusException("message"));
        mvc.perform(patch(API + "/{eventId}/reject", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(jsonPath("$.message", is("message")));

    }

    @Test
    void test4_1findAllEvents() throws Exception {
        Long[] users = {1L, 2L};
        Long[] catId = {1L, 2L};
        Long[] locIds = {1L};
        String[] states = {"PENDING", "PUBLISHED", "CANCELED"};
        String rangeStart = "2022-01-01 10:10:10";
        String rangeEnd = "2022-01-01 20:10:10";
        Integer from = 0;
        Integer size = 10;
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(users, states, catId,locIds, rangeStart, rangeEnd, from, size);
        mvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("users", "1")
                        .param("users", "2")
                        .param("states", "PENDING")
                        .param("states", "PUBLISHED")
                        .param("states", "CANCELED")
                        .param("categories", "1")
                        .param("categories", "2")
                        .param("rangeStart", "2022-01-01 10:10:10")
                        .param("rangeEnd", "2022-01-01 20:10:10")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());


    }
/*    @RequestParam(value = "users",required = false) Long[] users,
    @RequestParam(value = "states",required = false) String[] states,
    @RequestParam(value = "categories", required = false) Long[] categories,
    @RequestParam(value = "rangeStart", required = false) String rangeStart,
    @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
    @PositiveOrZero
    @RequestParam(value = "from", defaultValue = "0") int from,
    @Positive
    @RequestParam(value = "size", defaultValue = "10") int size)*/

}