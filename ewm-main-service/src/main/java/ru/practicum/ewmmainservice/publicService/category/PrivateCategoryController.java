package ru.practicum.ewmmainservice.publicService.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;

import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PrivateCategoryController {
    private final PrivateCategoryService service;

    @GetMapping
    public Collection<CategoryDto> findAllCategory() {
        return service.findAllCategory();
    }

    @GetMapping("/{catId}")
    public Category findCategoryById(@Positive @PathVariable("catId") Long catId) throws NotFoundException {
        return service.findCategoryById(catId);
    }
}
