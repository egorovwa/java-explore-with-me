package ru.practicum.ewmmainservice.publicService.category;

import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;

import java.util.Collection;


public interface PrivatCategoryService {
    Collection<CategoryDto> findAllCategory();

    Category findCategoryById(Long catId) throws NotFoundException;
}
