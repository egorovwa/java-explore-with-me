package ru.practicum.ewmmainservice.adminservice.category;

import com.example.evmdtocontract.dto.category.CategoryDto;
import com.example.evmdtocontract.dto.category.NewCategoryDto;
import lombok.RequiredArgsConstructor;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;

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
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) throws NotFoundException {
        return categoryService.patchCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable("catId") Long catId) throws NotFoundException, RelatedObjectsPresent {
        categoryService.deleteCategory(catId);
    }
}
