package ru.practicum.ewmmainservice.publicservice.category;

import com.example.evmdtocontract.dto.category.CategoryDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;

import java.util.Collection;


public interface PrivateCategoryService {
    Collection<CategoryDto> findAllCategory();

    Category findCategoryById(Long catId) throws NotFoundException;
}
