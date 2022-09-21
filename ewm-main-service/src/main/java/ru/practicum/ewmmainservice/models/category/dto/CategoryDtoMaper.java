package ru.practicum.ewmmainservice.models.category.dto;

import ru.practicum.ewmmainservice.models.category.Category;

public class CategoryDtoMaper {
    public Category fromNewCategoryDto(NewCategoryDto newCategoryDto) {
        return new Category(null, newCategoryDto.getName());
    }

    public Category fromDto(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
