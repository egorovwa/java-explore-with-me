package ru.practicum.ewmmainservice.adminservice.compilation;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import com.example.evmdtocontract.dto.compilation.NewCompilationDto;
import com.example.evmdtocontract.dto.event.EventShortDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.ForAllControllerErrorHendler;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompilationController.class)
@Import(ForAllControllerErrorHendler.class)
class CompilationControllerTest {
    private static final String API = "/admin/compilations";
    @MockBean
    CompilationService service;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

    @BeforeEach
    void setUp(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }

    @Test
    void test1_1createCompilation() throws Exception {
        NewCompilationDto newDto = new NewCompilationDto(List.of(1L), true, "title");
        EventShortDto event = new EventShortDto();
        CompilationDto compilation = new CompilationDto(List.of(event), 1L, true, "title");
        mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newDto)))
                .andExpect(status().isOk());
        verify(service, times(1)).createCompilation(newDto);
    }

    @Test
    void test1_2createCompilation_whenTextBlank() throws Exception {
        NewCompilationDto newDto = new NewCompilationDto(List.of(1L), true, "");
        EventShortDto event = new EventShortDto();
        CompilationDto compilation = new CompilationDto(List.of(event), 1L, true, "title");
        mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is("One or more fields are not valid.")));
        verify(service, times(0)).createCompilation(newDto);
    }

    @Test
    void test1_3createCompilation_whenEventNotFound() throws Exception {
        NewCompilationDto newDto = new NewCompilationDto(List.of(1L), true, "title");
        EventShortDto event = new EventShortDto();
        CompilationDto compilation = new CompilationDto(List.of(event), 1L, true, "title");
        when(service.createCompilation(newDto))
                .thenThrow(new FiledParamNotFoundException("messange"));
        mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is("One or more class fields do not exist.")));

    }

    @Test
    void test2_1deleteCompilation() throws Exception {
        mvc.perform(delete(API + "/{compId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8));
        verify(service, times(1)).deleteCompilation(1L);
    }

    @Test
    void test2_2deleteCompilation_whenNotFound() throws Exception {
        doThrow(new NotFoundException("id", "1", "Compilation"))
                .when(service).deleteCompilation(1L);
        mvc.perform(delete(API + "/{compId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason", is("The required object was not found.")));

    }

    @Test
    void test3_1deleteEventFromCompilation() throws Exception {
        mvc.perform(delete(API + "/{compId}/events/{eventId}", 1, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).deleteEventFromCompilation(1L, 1L);
    }

    @Test
    void twst3_2deleteEventFromCompilation_whenCompilationNotFound() throws Exception {
        doThrow(new NotFoundException("id", "1", "Compilation")).when(service)
                .deleteEventFromCompilation(1L, 1L);
        mvc.perform(delete(API + "/{compId}/events/{eventId}", 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void twst3_3deleteEventFromCompilation_whenEventNotFound() throws Exception {
        doThrow(new FiledParamNotFoundException("messange")).when(service)
                .deleteEventFromCompilation(1L, 1L);
        mvc.perform(delete(API + "/{compId}/events/{eventId}", 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test4_1addEventToCompilation() throws Exception {
        mvc.perform(patch(API + "/{compId}/events/{eventId}", 1, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).addEventToCompilation(1L, 1L);
    }

    @Test
    void test4_2addEventToCompilation() throws Exception {
        doThrow(new NotFoundException("id", "1", "Compilation")).when(service)
                .addEventToCompilation(1L, 1L);
        mvc.perform(patch(API + "/{compId}/events/{eventId}", 1, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void test5_1deletePinned() throws Exception {
        mvc.perform(delete(API + "/{compId}/pin", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).deletePinned(1L);

    }

    @Test
    void test6_addPinned() throws Exception {
        mvc.perform(patch(API + "/{compId}/pin", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(service, times(1)).addPinned(1L);

    }
}