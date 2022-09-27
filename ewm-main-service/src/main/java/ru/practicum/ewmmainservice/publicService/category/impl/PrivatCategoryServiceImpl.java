package ru.practicum.ewmmainservice.publicService.category.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.publicService.category.PrivatCategoryService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivatCategoryServiceImpl implements PrivatCategoryService {
    private final CategoryService adminServicr;
    private final CategoryRepository repository;
    private final CategoryDtoMaper dtoMaper;
    @Override
    public Collection<CategoryDto> findAllCategory() {
        return repository.findAll().stream().map(dtoMaper::toDto).collect(Collectors.toList());
    }

    @Override
    public Category findCategoryById(Long catId) throws NotFoundException {
        return adminServicr.findByid(catId);
    }
}
