package ru.practicum.ewmmainservice.models.category.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.category.Category;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CategoryDtoMaperTest {
    CategoryDtoMaper maper = new CategoryDtoMaper();

    @Test
    void fromNewCategoryDto() {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("name");
        Category category = new Category(null, "name");
        assertThat(maper.fromNewCategoryDto(newCategoryDto), is(category));
    }

    @Test
    void fromDto() {
        CategoryDto dto = new CategoryDto(1L, "name");
        Category category = new Category(1L, "name");
        assertThat(maper.fromDto(dto), is(category));
    }

    @Test
    void toDto() {
        CategoryDto dto = new CategoryDto(1L, "name");
        Category category = new Category(1L, "name");
        assertThat(maper.toDto(category), is(dto));
    }
}