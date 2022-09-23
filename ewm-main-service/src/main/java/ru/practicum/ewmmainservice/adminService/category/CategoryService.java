package ru.practicum.ewmmainservice.adminService.category;

import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto) throws ModelAlreadyExistsException;

    CategoryDto patchCategory(CategoryDto categoryDto) throws NotFoundException;

    void deleteCategory(Long catId) throws NotFoundException, RelatedObjectsPresent;

    Category findByid(Long category) throws NotFoundException;
}
