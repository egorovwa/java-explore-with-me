package ru.practicum.ewmmainservice.adminService.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.exceptions.UserAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto) throws UserAlreadyExistsException;

    CategoryDto patchCategory(CategoryDto categoryDto) throws UserNotFoundException;

    void deleteCategory(Long catId) throws UserNotFoundException, RelatedObjectsPresent;
}
