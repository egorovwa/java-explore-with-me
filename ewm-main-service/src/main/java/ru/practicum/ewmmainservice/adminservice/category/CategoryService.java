package ru.practicum.ewmmainservice.adminservice.category;

import com.example.evmdtocontract.dto.category.CategoryDto;
import com.example.evmdtocontract.dto.category.NewCategoryDto;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.models.category.Category;


public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto) throws ModelAlreadyExistsException;

    CategoryDto patchCategory(CategoryDto categoryDto) throws NotFoundException;

    void deleteCategory(Long catId) throws NotFoundException, RelatedObjectsPresent;

    Category findByid(Long category) throws NotFoundException;
}
