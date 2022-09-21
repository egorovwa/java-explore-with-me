package ru.practicum.ewmmainservice.adminService.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) throws ModelAlreadyExistsException {
        return categoryService.createCategory(newCategoryDto);
    }
    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) throws UserNotFoundException {
        return categoryService.patchCategory(categoryDto);
    }
    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable("catId") Long catId) throws UserNotFoundException, RelatedObjectsPresent {
        categoryService.deleteCategory(catId);
    }
}
