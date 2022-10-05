package ru.practicum.ewmmainservice.publicService.compilations;

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
import ru.practicum.ewmmainservice.utils.PageParam;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicCompilationsController.class)
class PublicCompilationsControllerTest {
    private static final String API = "/compilations";
    @MockBean
    PublicCompilationsService service;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }

    @Test
    void findCompilations() throws Exception {
        mvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("pinned", String.valueOf(true))
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
        verify(service, times(1)).findCompilations(true,
                PageParam.createPageable(0, 10));
    }

    @Test
    void findCompilation() throws Exception {
        mvc.perform(get(API + "/{compId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).findCompilation(1L);
    }
}