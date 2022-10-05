package ru.practicum.ewmmainservice.publicService.category;

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

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrivatCategoryController.class)
class PrivatCategoryControllerTest {
    private static final String API = "/categories";
    @MockBean
    PrivatCategoryService privatCategoryService;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }

    @Test
    void findAllCategory() throws Exception {
        mvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(privatCategoryService, times(1)).findAllCategory();
    }

    @Test
    void findCategoryById() throws Exception {
        mvc.perform(get(API + "/{catId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(privatCategoryService, times(1)).findCategoryById(1L);
    }
}