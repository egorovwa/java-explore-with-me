package ru.practicum.ewmmainservice.adminService.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);
    }
    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.patchCategory(categoryDto);
    }
    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable("catId") Long catId){
        categoryService.deleteCategory(catId);
    }
}
