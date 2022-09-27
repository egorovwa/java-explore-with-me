package ru.practicum.ewmmainservice.adminService.category.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDtoMaper;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryDtoMaper dtoMaper;
    private final AdminEwentRepository adminEwentRepository;


    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) throws ModelAlreadyExistsException {

        try {
            log.info("Create category {}", newCategoryDto);

            return dtoMaper.toDto(repository.save(dtoMaper.fromNewCategoryDto(newCategoryDto)));
        } catch (DataIntegrityViolationException e) {
            log.warn("Category {} alredy exist", newCategoryDto);
            throw new ModelAlreadyExistsException("name", newCategoryDto.getName(), "Category");
        }
    }

    @Override
    public CategoryDto patchCategory(CategoryDto categoryDto) throws NotFoundException {
        Category category = repository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException("id", String.valueOf(categoryDto.getId()), "Category"));
        category.setName(categoryDto.getName());
        log.info("update category {}", categoryDto);
        return dtoMaper.toDto(repository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) throws NotFoundException, RelatedObjectsPresent {
        Collection<Event> relatedEvents = adminEwentRepository.findAllByCategoryId(catId);
        if (relatedEvents.size() == 0) {
            try {
                repository.deleteById(catId);
                log.info("Delete category id = {}", catId);
            } catch (EmptyResultDataAccessException e) {
                log.warn("Caregory with id={} was not found", catId);
                throw new NotFoundException("id", catId.toString(), "Category");
            }
        } else {
            log.warn("The category id = {} is related to events: {}", catId, relatedEvents);
            throw new RelatedObjectsPresent("There are related events.", "Event",
                    relatedEvents.stream().map(Event::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public Category findByid(Long categoryId) throws NotFoundException {
        log.debug("Find category id = {}", categoryId);
        return repository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("id", categoryId.toString(), "Category"));
    }
}
