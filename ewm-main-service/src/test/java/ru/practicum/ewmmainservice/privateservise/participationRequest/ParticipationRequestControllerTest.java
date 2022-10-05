package ru.practicum.ewmmainservice.privateservise.participationRequest;

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
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.IlegalUserIdException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NumberParticipantsExceededException;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipationRequestController.class)
class ParticipationRequestControllerTest {
    @MockBean
    ParticipationRequestService service;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;
    final String API = "/users";

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }

    @Test
    void test1_1createRequest() throws Exception {
        mvc.perform(post(API + "/{userId}/requests", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("eventId", "1"))
                .andExpect(status().isOk());
        verify(service, times(1)).createRequest(1L, 1L);
    }

    @Test
    void test1_2createRequest_whenNumberParticipantsExceededException() throws Exception {
        when(service.createRequest(1L, 1L))
                .thenThrow(new NumberParticipantsExceededException(10));
        mvc.perform(post(API + "/{userId}/requests", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("eventId", "1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.reason", is("The limit of participants has been exceeded.")));
    }

    @Test
    void test2_1_cancelRequest() throws Exception {
        mvc.perform(patch(API + "/{userId}/requests/{requestId}/cancel", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).cancelRequest(1L, 1L);
    }

    @Test
    void test2_2_cancelRequest_whenIllegaUserIdException() throws Exception {
        when(service.cancelRequest(1L, 1L))
                .thenThrow(new IlegalUserIdException(1L, 1L, "Event"));
        mvc.perform(patch(API + "/{userId}/requests/{requestId}/cancel", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.reason", is("The User does not have access to the requested Object.")));

    }

    @Test
    void test3_findRequests() throws Exception {
        mvc.perform(get(API + "/{userId}/requests", 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).findRequests(1L);
    }

    @Test
    void test4_1confirmRequest() throws Exception {
        mvc.perform(patch(API + "/{userId}/events/{eventId}/requests/{reqId}/confirm", 1L, 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).confirmRequest(1L, 1L, 1L);
    }

    @Test
    void test4_2confirmRequest_whenFiledParamNotFoundException() throws Exception {
        when(service.confirmRequest(1L, 1L, 1L))
                .thenThrow(FiledParamNotFoundException.class);
        mvc.perform(patch(API + "/{userId}/events/{eventId}/requests/{reqId}/confirm", 1L, 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test5_rejectRequest() throws Exception {
        mvc.perform(patch(API + "/{userId}/events/{eventId}/requests/{reqId}/reject", 1, 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, times(1)).rejectRequest(1L, 1L, 1L);
    }

    @Test
    void finndRequestEventByUser() throws Exception {
        mvc.perform(get(API + "/{userId}/events/{eventId}/requests", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).finndRequestEventByUser(1L, 1L);
    }
}