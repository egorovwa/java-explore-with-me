package ru.practicum.ewmmainservice.adminService.category.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.event.EventService;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.exceptions.UserAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryDtoMaper dtoMaper;
    private final EventService eventService;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) throws UserAlreadyExistsException {

        try {
            log.info("Create category {}", newCategoryDto);
            return dtoMaper.toDto(repository.save(dtoMaper.fromNewCategoryDto(newCategoryDto)));
        } catch (DataIntegrityViolationException e) {
            log.warn("Category {} alredy exist", newCategoryDto);
            throw new UserAlreadyExistsException("Category already exist", "name", newCategoryDto.getName());
        }
    }

    @Override
    public CategoryDto patchCategory(CategoryDto categoryDto) throws UserNotFoundException {
        Category category = repository.findById(categoryDto.getId())
                .orElseThrow(() -> new UserNotFoundException("Category not found", "id", String.valueOf(categoryDto.getId())));
        log.info("update category {}", categoryDto);
        return dtoMaper.toDto(category);
    }

    @Override
    public void deleteCategory(Long catId) throws UserNotFoundException, RelatedObjectsPresent {
        Collection<Event> relatedEvents = eventService.findByCategoryId(catId);
        if (relatedEvents.size() == 0) {
            try {
                repository.deleteById(catId);
                log.info("Delete category id = {}", catId);
            } catch (EmptyResultDataAccessException e) {
                log.warn("Caregory id = {} not found", catId);
                throw new UserNotFoundException("Category not found", "id", catId.toString());
            }
        } else {
            log.warn("The category id = {} is related to events: {}", catId, relatedEvents);
            throw new RelatedObjectsPresent("There are related events.", "Event",
                    relatedEvents.stream().map(Event::getId).collect(Collectors.toList()));
        }
    }
}
