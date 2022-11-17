package ru.practicum.ewmmainservice.publicservice.category;

import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;

import java.util.Collection;


public interface PrivateCategoryService {
    Collection<CategoryDto> findAllCategory();

    Category findCategoryById(Long catId) throws NotFoundException;
}
