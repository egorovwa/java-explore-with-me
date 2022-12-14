package ru.practicum.ewmmainservice.publicservice.category.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewmmainservice.adminservice.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminservice.category.CategoryService;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivateCategoryServiceImplTest {
    @InjectMocks
    PrivateCategoryServiceImpl service;
    @Mock
    private CategoryService adminServicr;
    @Mock
    private CategoryRepository repository;
    @Mock
    private CategoryDtoMaper dtoMaper;

    @Test
    void findAllCategory() {
        when(repository.findAll())
                .thenReturn(List.of(new Category(1L, "name")));
        service.findAllCategory();
        verify(repository, times(1)).findAll();
    }

    @Test
    void findCategoryById() throws NotFoundException {
        service.findCategoryById(1L);
        verify(adminServicr, times(1)).findByid(1L);
    }
}