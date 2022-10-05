package ru.practicum.ewmmainservice.adminservice.category;

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
import ru.practicum.ewmmainservice.exceptions.ForAllControllerErrorHendler;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import(ForAllControllerErrorHendler.class)
class CategoryControllerTest {
    private static final String API = "/admin/categories";
    @MockBean
    CategoryService categoryService;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }

    @Test
    void test1_1createCategory() throws Exception {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("name");
        mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newCategoryDto)))
                .andExpect(status().isOk());
        verify(categoryService, times(1)).createCategory(newCategoryDto);
    }

    @Test
    void test1_2createCategory_withBlankName() throws Exception {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("");
        mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newCategoryDto)))
                .andExpect(status().isBadRequest());
        verify(categoryService, times(0)).createCategory(newCategoryDto);
    }

    @Test
    void test1_3createCategory_alredyExist() throws Exception {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("name");
        when(categoryService.createCategory(newCategoryDto))
                .thenThrow(new ModelAlreadyExistsException("message", "param", "value"));
        mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newCategoryDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("value message = param already exist.")));
    }


    @Test
    void test2_1updateCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto(1L, "name");
        mvc.perform(patch(API)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(mapper.writeValueAsString(categoryDto)));
        verify(categoryService, times(1)).patchCategory(categoryDto);
    }

    @Test
    void test2_2updateCategory_nameBlanc() throws Exception {
        CategoryDto categoryDto = new CategoryDto(1L, "");
        mvc.perform(patch(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(categoryDto)))
                .andExpect(status().isBadRequest());
        verify(categoryService, times(0)).patchCategory(categoryDto);
    }

    @Test
    void test3_1deleteCategory_withRelatedObjectsPresent() throws Exception {
        doThrow(new RelatedObjectsPresent("message", "Event", List.of(1L, 2L, 3L)))
                .when(categoryService).deleteCategory(1L);
        mvc.perform(delete(API + "/{catId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isConflict());
    }

    @Test
    void test3_2deleteCategory_notFound() throws Exception {
        doThrow(NotFoundException.class).when(categoryService).deleteCategory(1L);
        mvc.perform(delete(API + "/{catId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound());
    }

    @Test
    void test3_3deleteCategory() throws Exception {
        mvc.perform(delete(API + "/{catId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
        verify(categoryService, times(1)).deleteCategory(1L);
    }

}