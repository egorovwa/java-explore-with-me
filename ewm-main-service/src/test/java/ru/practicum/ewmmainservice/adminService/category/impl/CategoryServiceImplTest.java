package ru.practicum.ewmmainservice.adminService.category.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.practicum.ewmmainservice.adminService.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminService.event.AdminEventService;
import ru.practicum.ewmmainservice.adminService.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    CategoryDtoMaper dtoMaper = new CategoryDtoMaper();
    CategoryRepository repository = Mockito.mock(CategoryRepository.class);
    AdminEwentRepository adminEwentRepository = Mockito.mock(AdminEwentRepository.class);
    CategoryServiceImpl service = new CategoryServiceImpl(repository, dtoMaper, adminEwentRepository);

    @Test
    void test1_1createCategory() throws ModelAlreadyExistsException {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("name");
        when(repository.save(new Category(null, "name")))
                .thenReturn(new Category(1L, "name"));
        service.createCategory(newCategoryDto);
        verify(repository, times(1)).save(new Category(null, "name"));
    }

    @Test
    void test1_2createCategory_whenAlredyExist() throws ModelAlreadyExistsException {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("name");
        when(repository.save(new Category(null, "name")))
                .thenThrow(DataIntegrityViolationException.class);
        assertThrows(ModelAlreadyExistsException.class, () -> {
            service.createCategory(newCategoryDto);
        });
    }

    @Test
    void test2_1patchCategory() throws NotFoundException {
        CategoryDto categoryDto = new CategoryDto(1L, "name");
        Category category = new Category(1L, "name");
        when(repository.findById(1L))
                .thenReturn(Optional.of(category));
        assertThat(service.patchCategory(categoryDto), is(categoryDto));
    }

    @Test
    void test2_3patchCategory_whenNotFound() throws NotFoundException {
        CategoryDto categoryDto = new CategoryDto(1L, "name");

        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.patchCategory(categoryDto));
    }

    @Test
    void test3_1deleteCategory() throws NotFoundException, RelatedObjectsPresent {
        when(adminEwentRepository.findAllByCategoryId(1L))
                .thenReturn(List.of());
        service.deleteCategory(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void test3_2deleteCategory_whenNotFound() throws NotFoundException, RelatedObjectsPresent {
        when(adminEwentRepository.findAllByCategoryId(1L))
                .thenReturn(List.of());
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(1L);
        assertThrows(NotFoundException.class, () -> {
            service.deleteCategory(1L);
        });
    }

    @Test
    void test3_4deleteCategory_whenRelatedObjectsPresent() throws NotFoundException, RelatedObjectsPresent {
        when(adminEwentRepository.findAllByCategoryId(1L))
                .thenReturn(List.of(new Event()));
        assertThrows(RelatedObjectsPresent.class, () -> {
            service.deleteCategory(1L);
        });
        verify(repository, times(0)).deleteById(1L);
    }
    @Test
    void test4_findByid() throws NotFoundException {
        Category category = new Category(1L, "name");
        when(repository.findById(1L))
                .thenReturn(Optional.of(category));
        assertThat(service.findByid(1L), is(category));
    }
    @Test
    void test4_1findByid_whenNotFound() throws NotFoundException {
        Category category = new Category(1L, "name");
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> service.findByid(1L));
    }
}